package com.wangzai.project.service;

import com.wangzai.project.entity.UserInformation;

import java.util.HashMap;
import java.util.List;

public interface UserService {


    public UserInformation findByID(int userID);

    public List<UserInformation> allUser(HashMap<String,Object> hashMap);

    public void addUser(UserInformation user);

    public UserInformation userLogin(HashMap<String, Object> map);

    public void updateLoginData(HashMap<String, Object> map);

    public void updateUserState(HashMap<String, Object> map);

    public void deleteUser(int userID);

    public void updateUser(UserInformation user);

    public List<UserInformation> selectByProperty(HashMap<String, Object> map);

}
