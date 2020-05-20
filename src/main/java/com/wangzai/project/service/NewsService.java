package com.wangzai.project.service;

import com.wangzai.project.entity.NewsInformation;

import java.util.HashMap;
import java.util.List;

public interface NewsService {

    /**
     * 根据最近动态id值查询一条数据
     *
     * @param newsID 要查找的最近动态id
     * @return 查询到的最近动态
     */
    public NewsInformation findById(int newsID);

    public List<NewsInformation> allNews(HashMap<String, Object> hashMap);

    /**
     * 添加最近动态
     *
     * @param news 要添加的最近动态
     */
    public void addNews(NewsInformation news);

    /**
     * 更新最近动态
     *
     * @param news 要修改的最近动态
     * @return 修改后的数据
     */
    public void updateNews(NewsInformation news);

    /**
     * 根据最近动态id值删除一条数据
     *
     * @param newsID 要删除的最近动态id
     */
    public void deleteNews(int newsID);

    /**
     * 根据最近动态id值修改最近动态发布状态,0为未发布，1为已发布
     *
     * @param map 要修改最近动态的id和状态
     * @return 修改后的数据
     */
    public void updateNewsStatus(HashMap<String, Object> map);

    public Integer getCountNews(HashMap<String, Object> hashMap);

    public List<NewsInformation> selectByProperty(HashMap<String, Object> hashMap);



}
