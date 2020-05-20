package com.wangzai.project.service;

import com.wangzai.project.entity.LoginInformation;

public interface LoginInformationService {

    public LoginInformation findById (int loginID);

    public void addLoginInformation(LoginInformation loginInformation);

}
