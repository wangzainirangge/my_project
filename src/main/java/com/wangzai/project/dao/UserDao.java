package com.wangzai.project.dao;

import com.wangzai.project.entity.UserInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UserDao {


    public UserInformation findByID(@Param("userID") int userID);

    public List<UserInformation> allUser(@Param("status") int status);

    public boolean addUser(@Param("user") UserInformation user);

    public UserInformation userLogin(HashMap<String, Object> map);

    public boolean updateLoginData(HashMap<String, Object> map);

    public boolean updateUserState(HashMap<String, Object> map);

    public boolean deleteUser(@Param("userID") int userID);

    public boolean updateUser(@Param("user") UserInformation user);

    public List<UserInformation> selectByProperty(HashMap<String, Object> map);

}
