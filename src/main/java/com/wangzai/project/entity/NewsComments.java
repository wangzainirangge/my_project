package com.wangzai.project.entity;

/**
 * 新闻留言实体类
 */
public class NewsComments {

    //留言主键
    private Integer commentsID;
    //新闻主键
    private Integer newsID;
    //用户主键
    private Integer useID;
    //留言内容
    private String content;
    //创建IP
    private String createIP;
    //创建时间
    private String createTime;
    //1 已审核 2 未审核
    private Integer status;

    public NewsComments() {
    }

    public Integer getCommentsID() {
        return commentsID;
    }

    public void setCommentsID(Integer commentsID) {
        this.commentsID = commentsID;
    }

    public Integer getNewsID() {
        return newsID;
    }

    public void setNewsID(Integer newsID) {
        this.newsID = newsID;
    }

    public Integer getUseID() {
        return useID;
    }

    public void setUseID(Integer useID) {
        this.useID = useID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NewsComments{" +
                "commentsID=" + commentsID +
                ", newsID=" + newsID +
                ", useID=" + useID +
                ", content='" + content + '\'' +
                ", createIP='" + createIP + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
