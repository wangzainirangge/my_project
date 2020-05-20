package com.wangzai.project.dao;

import com.wangzai.project.entity.Manage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ManageDao {


    public Manage findByID(@Param("manageID") int manageID);

    public List<Manage> allManage(HashMap<String, Object> hashMap);

    public boolean addManage(@Param("manage") Manage manage);

    public Manage manageLogin(HashMap<String, Object> hashMap);

    public boolean updateManageState(HashMap<String, Object> hashMap);

    public boolean deleteManage(@Param("manageID") int manageID);

    public boolean updateManage(@Param("manage") Manage manage);

    public List<Manage> selectByProperty(HashMap<String, Object> hashMap);

}
