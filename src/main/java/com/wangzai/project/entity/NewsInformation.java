package com.wangzai.project.entity;

/**
 * 新闻实体类
 */
public class NewsInformation {

    //新闻主键
    private Integer newsID;
    //作者主键
    private Integer managerID;
    //标题
    private String title;
    //图片路径
    private String photoUrl;
    //1 已发布 2 未发布
    private Integer status;
    //新闻类型
    private Integer type;
    //文本路径
    private String htmlUrl;
    //标签
    private String label;
    //创建时间
    private String createTime;
    //修改时间
    private String modifyTime;
    //发布时间
    private String pushTime;
    //摘要
    private String articleAbstract;
    //图片数据
    private String imgBase;
    //新闻内容
    private String msgTXT;

    public NewsInformation() {
    }

    public Integer getNewsID() {
        return newsID;
    }

    public void setNewsID(Integer newsID) {
        this.newsID = newsID;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public String getImgBase() {
        return imgBase;
    }

    public void setImgBase(String imgBase) {
        this.imgBase = imgBase;
    }

    public String getMsgTXT() {
        return msgTXT;
    }

    public void setMsgTXT(String msgTXT) {
        this.msgTXT = msgTXT;
    }

    @Override
    public String toString() {
        return "NewsInformation{" +
                "newsID=" + newsID +
                ", managerID=" + managerID +
                ", title='" + title + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", label='" + label + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", pushTime='" + pushTime + '\'' +
                ", articleAbstract='" + articleAbstract + '\'' +
                ", imgBase='" + imgBase + '\'' +
                ", msgTXT='" + msgTXT + '\'' +
                '}';
    }
}
