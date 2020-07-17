package com.m2comm.ultrasound.DTO;

public class MenuDTO {

    String title;
    String url;

    public MenuDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
