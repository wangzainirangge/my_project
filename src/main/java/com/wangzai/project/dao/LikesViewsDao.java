package com.wangzai.project.dao;

import com.wangzai.project.entity.LikesViews;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface LikesViewsDao {

    public LikesViews findById (@Param("likeViewsId") int likeViewsId);


    public boolean addLikesViews(@Param("likesViews") LikesViews likesViews);

    public int getStartNum(HashMap<String,Object> hashMap);

}
