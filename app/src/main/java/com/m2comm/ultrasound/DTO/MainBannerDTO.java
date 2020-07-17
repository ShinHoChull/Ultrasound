package com.m2comm.ultrasound.DTO;

public class MainBannerDTO {

    private String sid;
    private String title;
    private String img;
    private String link;

    public MainBannerDTO(String sid, String title, String img, String link) {
        this.sid = sid;
        this.title = title;
        this.img = img;
        this.link = link;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSid() {
        return sid;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getLink() {
        return link;
    }

}
