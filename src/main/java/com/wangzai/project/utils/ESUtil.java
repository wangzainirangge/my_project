package com.wangzai.project.utils;

import com.alibaba.fastjson.JSON;
import com.wangzai.project.entity.FindUrls;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ESUtil {


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    //把数据存储到ES
    public void searchSave(FindUrls findUrls, String indexName){
        //把对象存储到指定索引中

        IndexRequest request = new IndexRequest(indexName);
        request.id(String.valueOf(findUrls.getID()));
        request.source(JSON.toJSONString(findUrls), XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    //从ES中删除指定数据
    public void searchDelete(int entityID,String indexName){
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest(indexName, String.valueOf(entityID)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //从ES中搜索指定数据并红色高亮
    public List<FindUrls> searchKeyword(String keyword, String indexName,int pageIndex,int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("title", keyword);
        data.put("textContent", keyword);
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
        }finally {
            return list;
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

    public static String delHTMLTag(String urls){
        //将html转换为纯文本，此方法最后保留了&nbps空格，使用时注意将空格替换掉String urls = "<p><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">浔阳江头夜送客，枫叶荻花秋瑟瑟。主人下马客在船，举酒欲饮无管弦。醉不成欢惨将别，别时茫茫江浸月。</span><br style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; white-space: normal; background-color: rgb(255, 255, 255);\"/><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">忽闻水上</span><span style=\"color: rgb(204, 0, 0); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">琵琶</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">声，主人忘归客不发。寻声暗问弹者谁，</span><span style=\"color: rgb(204, 0, 0); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">琵琶</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">声停欲语迟。移船相近邀相见，添酒回灯重开宴。千呼万唤始出来，犹抱</span><span style=\"color: rgb(204, 0, 0); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">琵琶</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">半遮面。转轴拨弦三两声，未成曲调先有情。弦弦掩抑声声思，似诉平生不得志。低眉信手续续弹，说尽心中无限事。轻拢慢捻抹复挑，初为《霓裳》后《六幺》。大弦嘈嘈如急雨，小弦切切如私语。嘈嘈切切错杂弹，大珠小珠落玉盘。间关莺语花底滑，幽咽泉流冰下难。冰泉冷涩弦凝绝，凝绝不通声暂歇。别有幽愁暗恨生，此时无声胜有声。银瓶乍破水浆迸，铁骑突出刀枪鸣。曲终收拨当心画，四弦一声如裂帛。东船西舫悄无言，唯见江心秋月白。</span><br style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; white-space: normal; background-color: rgb(255, 255, 255);\"/><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">沉吟放拨插弦中，整顿衣裳起敛容。自言本是京城女，家在虾蟆陵下住。十三学得</span><span style=\"color: rgb(204, 0, 0); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">琵琶</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">成，名属教坊第一部。曲罢曾教善才服，妆成每被秋娘妒。五陵年少争缠头，一曲红绡不知数。钿头银篦击节碎，血色罗裙翻酒污。今年欢笑复明年，秋月春风等闲度。弟走从军阿姨死，暮去朝来颜色故。门前冷落鞍马稀，老大嫁作商人妇。商人重利轻别离，前月浮梁买茶去。去来江口守空船，绕船月明江水寒。夜深忽梦少年事，梦啼妆泪红阑干。</span><br style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; white-space: normal; background-color: rgb(255, 255, 255);\"/><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">我闻</span><span style=\"color: rgb(204, 0, 0); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">琵琶</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">已叹息，又闻此语重唧唧。同是天涯沦落人，相逢何必曾相识！我从去年辞帝京，谪居卧病浔阳城。浔阳地僻无音乐，终岁不闻丝竹声。住近湓江地低湿，黄芦苦竹绕宅生。其间旦暮闻何物？杜鹃啼血猿哀鸣。春江花朝秋月夜，往往取酒还独倾。岂无山歌与村笛？呕哑嘲哳难为听。今夜闻君</span><span style=\"color: rgb(204, 0, 0); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">琵琶</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">语，如听仙乐耳暂明。莫辞更坐弹一曲，为君翻作《</span><span style=\"color: rgb(204, 0, 0); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">琵琶行</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, SimHei; font-size: 13.91px; background-color: rgb(255, 255, 255);\">》。感我此言良久立，却坐促弦弦转急。凄凄不似向前声，满座重闻皆掩泣。座中泣下谁最多？江州司马青衫湿。</span></p>";
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script= Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(urls);
        urls=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(urls);
        urls=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(urls);
        urls=m_html.replaceAll(""); //过滤html标签
        //System.out.println(urls.trim());//返回文本字符串
        return urls.trim();
    }

}
