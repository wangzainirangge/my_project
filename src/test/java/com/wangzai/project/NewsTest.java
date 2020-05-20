package com.wangzai.project;

import com.wangzai.project.entity.FindUrls;
import com.wangzai.project.entity.NewsInformation;
import com.wangzai.project.service.Impl.NewsServiceImpl;
import com.wangzai.project.utils.ESUtil;
import com.wangzai.project.utils.NowTimeUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsTest {

    @Autowired
    private NewsServiceImpl newsService;
    @Autowired
    private ESUtil esUtil;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Test
    public void addNews() {
        NewsInformation newsInformation = new NewsInformation();
        newsInformation.setManagerID(1);
        newsInformation.setTitle("aaaaaa");
        newsInformation.setPhotoUrl("wwww/wwww/ccccc");
        newsInformation.setType(4);
        newsInformation.setHtmlUrl("tttt/tettet:etert");
        newsInformation.setLabel("shgsghslgos");
        newsInformation.setCreateTime(NowTimeUtil.nowTime());
        newsInformation.setArticleAbstract("是高手高手的话给路上都会给上课·");
        newsService.addNews(newsInformation);

    }

    @Test
    public void allNews(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("title","");
        hashMap.put("startTime","2000-01-01");
        hashMap.put("endTime","2111-12-31");
        hashMap.put("author","张");
        hashMap.put("status",2);
        hashMap.put("type",1);
        hashMap.put("pageSize",5);
        hashMap.put("pageNo",1);
        System.out.println(newsService.selectByProperty(hashMap).size());

    }
    @Test
    public void test02(){
        FindUrls findUrls = new FindUrls();
        findUrls.setID(1);
        findUrls.setTitle("aaaaaaaaa");
        findUrls.setTextContent("aaaaaaaa");
        findUrls.setType(1);
        findUrls.setState(1);
        findUrls.setNewsID(1);
        findUrls.setPublicTime(NowTimeUtil.nowTime());
        esUtil.searchSave(findUrls,"aaa");
    }

    @Test
    public void test03(){
        esUtil.searchDelete(1,"aaa");
    }

    @Test
    public void test(){
        Integer pageIndex = 1;
        Integer pageSize = 5;
        String indexName = "news";
        Map<String, Object> data = new HashMap<>();
        data.put("title", "一");
        data.put("textContent", "一");
        List<FindUrls> list = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        SearchRequest searchRequest = new SearchRequest(indexName);
        // searchRequest.types(indexName);
        queryBuilder(pageIndex, pageSize, data, indexName, searchRequest);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits().getHits()) {
                //System.out.println(hit);//ES形式数据
                //System.out.println(hit.getSourceAsMap());//HashMap格式数据
                /*Map<String, Object> map = hit.getSourceAsMap();
                map.put("id", hit.getId());
                result.add(map);*/
                FindUrls findUrls = new FindUrls();
                Map<String, Object> map = hit.getSourceAsMap();
                findUrls.setID(Integer.valueOf(hit.getId()));
                findUrls.setNewsID(Integer.valueOf(map.get("newsID").toString()));
                findUrls.setType(Integer.valueOf(map.get("type").toString()));
                findUrls.setState(Integer.valueOf(map.get("state").toString()));
                findUrls.setPublicTime(map.get("publicTime").toString());
                // 取高亮结果
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlightTitle = highlightFields.get("title");
                if (highlightTitle!=null){
                    Text[] titleText = highlightTitle.fragments(); // 多值的字段会有多个值
                    String fragmentString = titleText[0].string();
                    findUrls.setTitle(fragmentString);
                    //System.out.println("标题高亮：" + fragmentString);
                }else {
                    findUrls.setTitle(map.get("title").toString());
                }
                HighlightField highlightTextContent = highlightFields.get("textContent");
                if (highlightTextContent!=null){
                    Text[] textContentText = highlightTextContent.fragments(); // 多值的字段会有多个值
                    String fragmentString = textContentText[0].string();
                    findUrls.setTextContent(fragmentString);
                    //System.out.println("文本高亮：" + fragmentString);
                }else {
                    findUrls.setTextContent(map.get("textContent").toString().substring(0,200));
                }
                list.add(findUrls);
            }
            System.out.println("pageIndex:" + pageIndex);
            System.out.println("pageSize:" + pageSize);
            System.out.println("数据总数为："+response.getHits().getTotalHits());
            System.out.println("获取了："+list.size()+"条数据");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void queryBuilder(Integer pageIndex, Integer pageSize, Map<String, Object> query, String indexName,
                              SearchRequest searchRequest) {
        if (query != null && !query.keySet().isEmpty()) {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            if (pageIndex != null && pageSize != null) {
                searchSourceBuilder.size(pageSize);
                if (pageIndex <= 0) {
                    pageIndex = 0;
                }
                searchSourceBuilder.from((pageIndex - 1) * pageSize);
            }
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            query.keySet().forEach(key -> {
                //should是两个中有一个存在即可
                boolBuilder.should(QueryBuilders.matchQuery(key, query.get(key)));
                //must是两个中必须都存在
                //boolBuilder.must(QueryBuilders.matchQuery(key, query.get(key)));
            });
            searchSourceBuilder.query(boolBuilder);

            HighlightBuilder highlightBuilder = new HighlightBuilder();
            //标题高亮
            HighlightBuilder.Field highlightTitle =
                    new HighlightBuilder.Field("title")
                            .preTags("<font color='red'>")
                            .postTags("</font>");
            highlightTitle.highlighterType("unified");
            //字段高亮显示类型，默认用标签包裹高亮字词
            highlightBuilder.field(highlightTitle);
            //文本高亮
            HighlightBuilder.Field highlightTextContent =
                    new HighlightBuilder.Field("textContent")
                            .preTags("<font color='red'>")
                            .postTags("</font>")
                            .fragmentSize(200);
            //字段高亮显示类型，默认用标签包裹高亮字词
            highlightTextContent.highlighterType("unified");
            highlightBuilder.field(highlightTextContent);
            searchSourceBuilder.highlighter(highlightBuilder);
            SearchRequest source = searchRequest.source(searchSourceBuilder);
        }
    }
}


