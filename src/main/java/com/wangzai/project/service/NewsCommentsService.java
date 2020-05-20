package com.wangzai.project.service;

import com.wangzai.project.entity.NewsComments;

import java.util.List;

public interface NewsCommentsService {

    public NewsComments findById (int commentsID);

    public List<NewsComments> allNewsComments (int status);

    public void addNewsComments(NewsComments newsComments);

    public void deleteNewsComments(int commentsID);

}
