package com.wangzai.project.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangzai.project.dao.NewsDao;
import com.wangzai.project.entity.FindUrls;
import com.wangzai.project.entity.NewsInformation;
import com.wangzai.project.entity.Search;
import com.wangzai.project.service.NewsService;
import com.wangzai.project.service.SearchService;
import com.wangzai.project.utils.ESUtil;
import com.wangzai.project.utils.RedisUtil;
//import io.searchbox.client.JestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

//@CacheConfig(cacheNames = "news",cacheManager = "expressCacheManager")
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ESUtil esUtil;;
    @Autowired
    private SearchService searchService;

    /**
     * 根据新闻id值查询一条数据
     * @param newsID            要查找的新闻id
     * @return                  查询到的新闻
     */
    //@Cacheable(key = "#newsID")
    @Override
    public NewsInformation findById(int newsID) {
        NewsInformation news = newsDao.findById(newsID);
        redisUtil.set("news:"+news.getNewsID().toString(),news);
        return news;
    }


    @Override
    public List<NewsInformation> allNews(HashMap<String,Object> hashMap) {
        int pageNo = Integer.valueOf(hashMap.get("pageNo").toString());
        int pageSize = Integer.valueOf(hashMap.get("pageSize").toString());
        Page page = PageHelper.startPage(pageNo,pageSize,true);
        List<NewsInformation> list = newsDao.allNews(hashMap);
        //查询数据总数
        //System.out.println(page.getTotal());
        return list;
    }

    /**
     *添加新闻
     * @param news              要添加的新闻
     */
    @Override
    public void addNews(NewsInformation news) {
        newsDao.addNews(news);
        Search search = new Search();
        search.setNewsID(news.getNewsID());
        search.setType(1);
        searchService.addSearch(search);
        int id = search.getId();
        //调用自定义的elasticsearch工具类把最近动态id、title、urls、实例名
        FindUrls findUrls = new FindUrls();
        findUrls.setID(id);
        findUrls.setTitle(news.getTitle());
        String textContent = ESUtil.delHTMLTag(news.getMsgTXT());
        findUrls.setTextContent(textContent);
        findUrls.setNewsID(news.getNewsID());
        findUrls.setType(news.getType());
        findUrls.setState(0);
        findUrls.setPublicTime(news.getPushTime());
        esUtil.searchSave(findUrls,"news");
    }

    /**
     * 更新新闻
     * @param news              要修改的新闻
     * @return                  返回修改后的数据
     */
    @Override
    public void updateNews(NewsInformation news) {
        newsDao.updateNews(news);
        redisUtil.set("news:"+news.getNewsID().toString(),findById(news.getNewsID()));
        //更新ES索引引擎中的ES
        HashMap<String,Object> map = new HashMap<>();
        map.put("newsID",news.getNewsID());
        map.put("type",1);
        Object searchID = searchService.selectID(map);
        int id = 0;
        if (searchID==null){
            Search search = new Search();
            search.setNewsID(news.getNewsID());
            search.setType(1);
            searchService.addSearch(search);
            id  = search.getId();
        } else{
            id = Integer.valueOf(searchID.toString());
        }
        //调用自定义的elasticsearch工具类把最近动态id、title、urls、实例名
        FindUrls findUrls = new FindUrls();
        findUrls.setID(id);
        findUrls.setTitle(news.getTitle());
        String textContent = ESUtil.delHTMLTag(news.getMsgTXT());
        findUrls.setTextContent(textContent);
        findUrls.setNewsID(news.getNewsID());
        findUrls.setType(news.getType());
        findUrls.setState(news.getStatus());
        findUrls.setPublicTime(news.getPushTime());
        esUtil.searchSave(findUrls,"news");
    }

    /**
     * 根据新闻id值删除一条数据
     * @param newsID            要删除的新闻id
     */
    @Override
    public void deleteNews(int newsID) {
        redisUtil.del("news:"+newsID);
        newsDao.deleteNews(newsID);
        HashMap<String,Object> map = new HashMap<>();
        map.put("newsID",newsID);
        map.put("type",1);
        Object searchID = searchService.selectID(map);
        if (searchID!=null){
            esUtil.searchDelete(Integer.valueOf(searchID.toString()),"news");
        }
    }

    /**
     * 根据最近动态id值修改新闻发布状态,0为未发布，1为已发布
     * @param map               要修改新闻的id和状态
     * @return                  修改后的数据
     */
    @Override
    public void updateNewsStatus(HashMap<String, Object> map) {
        newsDao.updateNewsStatus(map);
        redisUtil.set("news:" + map.get("newsID"),findById((int)map.get("newsID")));
    }

    @Override
    public Integer getCountNews(HashMap<String, Object> hashMap) {
        return newsDao.getCountNews(hashMap);
    }

    @Override
    public List<NewsInformation> selectByProperty(HashMap<String, Object> hashMap) {
        int pageNo = Integer.valueOf(hashMap.get("pageNo").toString());
        int pageSize = Integer.valueOf(hashMap.get("pageSize").toString());
        Page page = PageHelper.startPage(pageNo,pageSize,true);
        List<NewsInformation> list = newsDao.selectByProperty(hashMap);
        return list;
    }

}
