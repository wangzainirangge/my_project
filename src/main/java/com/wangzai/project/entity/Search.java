package com.wangzai.project.entity;

public class Search {
    //检索主键id，自增
    private int id;
    //新闻id
    private int newsID;
    //新闻类型
    private int type;

    public Search() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", newsID=" + newsID +
                ", type=" + type +
                '}';
    }
}
