package com.example.sharedspace.ApiModels;

public class Post {
    private int slno;
    private String title;
    private String content;
    private String author;
    private String postslug;
    private String timestamp;
    private  int user;

    public int getSlno() {
        return slno;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getPostslug() {
        return postslug;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getUser() {
        return user;
    }
}
