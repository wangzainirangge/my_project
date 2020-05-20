package com.wangzai.project.entity;

/**
 * 管理人员实体类
 */
public class Manage {

    //管理人员主键
    private Integer manageID;
    //用户名
    private String username;
    //用户密码
    private String password;
    //真实姓名
    private String realName;
    //性别 1男，2女
    private Integer gender;
    //部门类型
    private Integer type;
    //用户等级  1 运营者   2 管理员
    private Integer identityLevel;
    //1 可用 2 禁用
    private Integer status;
    //邮箱
    private String email;
    //创建时间
    private String createTime;
    //最新修改时间
    private String modifyTime;
    //创建IP
    private String registeredIP;
    //最后登录时间
    private String lastLoginTime;
    //最后登录IP
    private String lastLoginIP;

    public Manage() {
    }

    public Integer getManageID() {
        return manageID;
    }

    public void setManageID(Integer manageID) {
        this.manageID = manageID;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIdentityLevel() {
        return identityLevel;
    }

    public void setIdentityLevel(Integer identityLevel) {
        this.identityLevel = identityLevel;
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

    public String getRegisteredIP() {
        return registeredIP;
    }

    public void setRegisteredIP(String registeredIP) {
        this.registeredIP = registeredIP;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    @Override
    public String toString() {
        return "ManageService{" +
                "manageID=" + manageID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", gender=" + gender +
                ", type=" + type +
                ", identityLevel=" + identityLevel +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", registeredIP='" + registeredIP + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", lastLoginIP='" + lastLoginIP + '\'' +
                '}';
    }
}
