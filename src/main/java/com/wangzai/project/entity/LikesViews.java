package com.wangzai.project.entity;

//新闻点赞/浏览信息实体类
public class LikesViews {

    //操作主键
    private Integer likeViewsId;
    //新闻主键
    private Integer newsId;
    //用户主键
    private Integer userId;
    //1浏览 2点赞 3留言
    private Integer type;
    //用户IP
    private String userIP;
    //操作时间
    private String operationTime;

    public LikesViews() {
    }

    public Integer getLikeViewsId() {
        return likeViewsId;
    }

    public void setLikeViewsId(Integer likeViewsId) {
        this.likeViewsId = likeViewsId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    public String toString() {
        return "LikesViews{" +
                "likeViewsId=" + likeViewsId +
                ", newsId=" + newsId +
                ", userId=" + userId +
                ", type=" + type +
                ", userIP='" + userIP + '\'' +
                ", operationTime='" + operationTime + '\'' +
                '}';
    }
}
