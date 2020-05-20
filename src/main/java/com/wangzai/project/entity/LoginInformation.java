package com.wangzai.project.entity;

/**
 * 登陆信息实体类
 */
public class LoginInformation {

    //登陆主键
    private Integer loginID;
    //登陆人员主键
    private Integer personID;
    //登陆IP
    private String loginIP;
    //登陆时间
    private String loginTime;
    //1 运营者 2 管理员 3 普通用户
    private Integer type;

    public LoginInformation() {
    }

    public Integer getLoginID() {
        return loginID;
    }

    public void setLoginID(Integer loginID) {
        this.loginID = loginID;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginInformation{" +
                "loginID=" + loginID +
                ", personID=" + personID +
                ", loginIP='" + loginIP + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", type=" + type +
                '}';
    }
}
