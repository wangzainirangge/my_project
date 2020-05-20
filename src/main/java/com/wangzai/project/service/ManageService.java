package com.wangzai.project.service;

import com.wangzai.project.entity.Manage;

import java.util.HashMap;
import java.util.List;

public interface ManageService {


    public Manage findByID(int manageID);

    public List<Manage> allManage(HashMap<String, Object> hashMap);

    public void addManage(Manage manage);

    public Manage manageLogin(HashMap<String, Object> hashMap);

    public void updateManageState(HashMap<String, Object> hashMap);

    public void deleteManage(int manageID);

    public void updateManage(Manage manage);

    public List<Manage> selectByProperty(HashMap<String, Object> hashMap);



}
