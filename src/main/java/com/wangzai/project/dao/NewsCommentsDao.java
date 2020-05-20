package com.wangzai.project.dao;

import com.wangzai.project.entity.NewsComments;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsCommentsDao {

    public NewsComments findById (@Param("commentsID") int commentsID);

    public List<NewsComments> allNewsComments (@Param("status") int status);

    public boolean addNewsComments(@Param("newsComments") NewsComments newsComments);

    public boolean deleteNewsComments(@Param("commentsID") int commentsID);

}
