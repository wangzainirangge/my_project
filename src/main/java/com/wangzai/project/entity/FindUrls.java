package com.wangzai.project.entity;

public class FindUrls {
    //站内搜索id
    //@JestId
    private int ID;
    //标题
    private String title;
    //文本内容
    private String textContent;
    //新闻id
    private int newsID;
    //新闻类型
    private int type;
    //实体发布状态
    private int state;
    //推送时间
    private String publicTime;
    public FindUrls() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    @Override
    public String toString() {
        return "FindUrls{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", textContent='" + textContent + '\'' +
                ", newsID=" + newsID +
                ", type=" + type +
                ", state=" + state +
                ", publicTime='" + publicTime + '\'' +
                '}';
    }
}
