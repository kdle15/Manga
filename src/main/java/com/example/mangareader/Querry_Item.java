package com.example.mangareader;

public class Querry_Item {
    private String title;
    private String url;

    public Querry_Item(String title, String url){
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
