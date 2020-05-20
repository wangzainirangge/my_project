package com.wangzai.project.entity;

/**
 * 用户信息实体类
 */
public class UserInformation {

    //用户主键
    private Integer userID;
    //用户名
    private String username;
    //用户密码
    private String password;
    //性别 1男，2女
    private Integer gender;
    //1 可用 2 禁用
    private Integer status;
    //邮箱
    private String email;
    //年龄
    private Integer age;
    //创建时间
    private String createTime;
    //最新修改时间
    private String modifyTime;
    //最后登录时间
    private String lastLoginTime;
    //创建IP
    private String registeredIP;
    //最后登录IP
    private String lastLoginIP;

    public UserInformation() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRegisteredIP() {
        return registeredIP;
    }

    public void setRegisteredIP(String registeredIP) {
        this.registeredIP = registeredIP;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", registeredIP='" + registeredIP + '\'' +
                ", lastLoginIP='" + lastLoginIP + '\'' +
                '}';
    }
}
