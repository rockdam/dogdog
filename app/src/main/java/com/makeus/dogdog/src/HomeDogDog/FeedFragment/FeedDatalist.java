package com.makeus.dogdog.src.HomeDogDog.FeedFragment;

public class FeedDatalist {


    String title; // 제목
    String content;
    String id;
    Boolean heartBoolean; // 자기가 해당 게시물에 하트표시했는가?

    public Boolean getHeartBoolean() {
        return heartBoolean;
    }

    public void setHeartBoolean(Boolean heartBoolean) {
        this.heartBoolean = heartBoolean;
    }

    public FeedDatalist(String title, String content, String id, Boolean heartBoolean) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.heartBoolean = heartBoolean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
