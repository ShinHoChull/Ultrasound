package com.m2comm.ultrasound.DTO;

public class NoticeDTO {


    private String sid;
    private String title;
    private String link;

    public NoticeDTO(String sid, String title, String link) {
        this.sid = sid;
        this.title = title;
        this.link = link;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLink() {
        return link;
    }
}
