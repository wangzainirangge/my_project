package com.wangzai.project.service.Impl;

import com.wangzai.project.dao.LikesViewsDao;
import com.wangzai.project.entity.LikesViews;
import com.wangzai.project.service.LikesViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LikesViewsServiceImpl implements LikesViewsService {

    @Autowired
    private LikesViewsDao likesViewsDao;

    @Override
    public LikesViews findById(int likeViewsId) {
        LikesViews likesViews = likesViewsDao.findById(likeViewsId);
        return likesViews;
    }

    @Override
    public void addLikesViews(LikesViews likesViews) {
        likesViewsDao.addLikesViews(likesViews);
    }

    @Override
    public int getStartNum(HashMap<String,Object> hashMap) {
        return likesViewsDao.getStartNum(hashMap);
    }
}
