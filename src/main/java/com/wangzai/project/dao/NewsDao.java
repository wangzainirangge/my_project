package com.wangzai.project.dao;

import com.wangzai.project.entity.NewsInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface NewsDao {

    /**
     * 根据新闻id值查询一条数据
     * @param newsID            要查找的新闻id
     * @return                  查询到的新闻
     */
    public NewsInformation findById (@Param("newsID") int newsID);


    public List<NewsInformation> allNews (HashMap<String,Object> hashMap);

    /**
     *添加新闻
     * @param news              要添加的新闻
     * @return                  处理结果
     */
    public boolean addNews(@Param("news") NewsInformation news);

    /**
     * 更新新闻
     * @param news              要修改的新闻
     * @return                  处理结果
     */
    public boolean updateNews(@Param("news") NewsInformation news);

    /**
     * 根据新闻id值删除一条数据
     * @param newsID            要删除的新闻id
     * @return                  处理结果
     */
    public boolean deleteNews(@Param("newsID") int newsID);

    /**
     * 根据新闻id值修改新闻发布状态,0为未发布，1为已发布
     * @param map               要修改新闻的id和状态
     * @return                  处理结果
     */
    public boolean updateNewsStatus(HashMap<String,Object> map);


    public Integer getCountNews(HashMap<String,Object> hashMap);


    public List<NewsInformation> selectByProperty(HashMap<String, Object> hashMap);
}
