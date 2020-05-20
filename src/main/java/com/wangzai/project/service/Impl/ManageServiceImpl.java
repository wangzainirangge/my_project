package com.wangzai.project.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangzai.project.dao.ManageDao;
import com.wangzai.project.entity.Manage;
import com.wangzai.project.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private ManageDao manageDao;

    @Override
    public Manage findByID(int manageID) {
        Manage manage = manageDao.findByID(manageID);
        return manage;
    }

    @Override
    public List<Manage> allManage(HashMap<String, Object> hashMap) {
        int pageNo = Integer.valueOf(hashMap.get("pageNo").toString());
        int pageSize = Integer.valueOf(hashMap.get("pageSize").toString());
        Page page = PageHelper.startPage(pageNo,pageSize,true);
        List<Manage> list = manageDao.allManage(hashMap);
        return list;
    }

    @Override
    public void addManage(Manage manage) {
        manageDao.addManage(manage);
    }

    @Override
    public Manage manageLogin(HashMap<String, Object> map) {
        Manage manage = manageDao.manageLogin(map);
        return manage;
    }

    @Override
    public void updateManageState(HashMap<String, Object> map) {
        manageDao.updateManageState(map);
    }

    @Override
    public void deleteManage(int manageID) {
        manageDao.deleteManage(manageID);
    }

    @Override
    public void updateManage(Manage manage) {
        manageDao.updateManage(manage);
    }

    @Override
    public List<Manage> selectByProperty(HashMap<String, Object> hashMap) {
        int pageNo = Integer.valueOf(hashMap.get("pageNo").toString());
        int pageSize = Integer.valueOf(hashMap.get("pageSize").toString());
        Page page = PageHelper.startPage(pageNo,pageSize,true);
        List<Manage> list = manageDao.selectByProperty(hashMap);
        return list;
    }
}
