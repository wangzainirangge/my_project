package com.wangzai.project.service;

import com.wangzai.project.entity.LikesViews;

import java.util.HashMap;

public interface LikesViewsService {

    public LikesViews findById (int likeViewsId);

    public void addLikesViews(LikesViews likesViews);

    public int getStartNum(HashMap<String,Object> hashMap);
}
