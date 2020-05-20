package com.wangzai.project.service.Impl;

import com.wangzai.project.dao.NewsCommentsDao;
import com.wangzai.project.entity.NewsComments;
import com.wangzai.project.service.NewsCommentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsCommentsServiceImpl implements NewsCommentsService {

    @Resource
    private NewsCommentsDao newsCommentsDao;

    @Override
    public NewsComments findById(int commentsID) {
        NewsComments newsComments = newsCommentsDao.findById(commentsID);
        return newsComments;
    }

    @Override
    public List<NewsComments> allNewsComments(int status) {
        List<NewsComments> list = newsCommentsDao.allNewsComments(status);
        return list;
    }

    @Override
    public void addNewsComments(NewsComments newsComments) {
        newsCommentsDao.addNewsComments(newsComments);
    }

    @Override
    public void deleteNewsComments(int commentsID) {
        newsCommentsDao.deleteNewsComments(commentsID);
    }
}
