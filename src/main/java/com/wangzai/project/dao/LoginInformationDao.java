package com.wangzai.project.dao;

import com.wangzai.project.entity.LoginInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInformationDao {

    public LoginInformation findById (@Param("loginID") int loginID);


    public boolean addLoginInformation(@Param("loginInformation") LoginInformation loginInformation);

}
