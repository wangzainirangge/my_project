package com.wangzai.project.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangzai.project.dao.UserDao;
import com.wangzai.project.entity.UserInformation;
import com.wangzai.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserInformation findByID(int userID) {
        return null;
    }

    @Override
    public List<UserInformation> allUser(HashMap<String,Object> hashMap) {
        int pageNo = Integer.valueOf(hashMap.get("pageNo").toString());
        int pageSize = Integer.valueOf(hashMap.get("pageSize").toString());
        Page page = PageHelper.startPage(pageNo,pageSize,true);
        List<UserInformation> list = userDao.allUser(Integer.valueOf(hashMap.get("status").toString()));
        return list;
    }

    @Override
    public void addUser(UserInformation user) {
        userDao.addUser(user);
    }

    @Override
    public UserInformation userLogin(HashMap<String, Object> map) {
        UserInformation userInformation = userDao.userLogin(map);
        return userInformation;
    }

    @Override
    public void updateLoginData(HashMap<String, Object> map) {
        System.out.println(map.toString());
        userDao.updateLoginData(map);
    }

    @Override
    public void updateUserState(HashMap<String, Object> map) {
        userDao.updateUserState(map);
    }

    @Override
    public void deleteUser(int userID) {
        userDao.deleteUser(userID);
    }

    @Override
    public void updateUser(UserInformation user) {
        userDao.updateUser(user);
    }

    @Override
    public List<UserInformation> selectByProperty(HashMap<String, Object> hashMap) {
        int pageNo = Integer.valueOf(hashMap.get("pageNo").toString());
        int pageSize = Integer.valueOf(hashMap.get("pageSize").toString());
        Page page = PageHelper.startPage(pageNo,pageSize,true);
        List<UserInformation> list = userDao.selectByProperty(hashMap);
        return list;
    }
}
