package com.wangzai.project.service.Impl;

import com.wangzai.project.dao.LoginInformationDao;
import com.wangzai.project.entity.LoginInformation;
import com.wangzai.project.service.LoginInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginInformationServiceImpl implements LoginInformationService {

    @Autowired
    private LoginInformationDao loginInformationDao;

    @Override
    public LoginInformation findById(int loginID) {
        LoginInformation loginInformation = loginInformationDao.findById(loginID);
        return loginInformation;
    }

    @Override
    public void addLoginInformation(LoginInformation loginInformation) {
        loginInformationDao.addLoginInformation(loginInformation);
    }
}
