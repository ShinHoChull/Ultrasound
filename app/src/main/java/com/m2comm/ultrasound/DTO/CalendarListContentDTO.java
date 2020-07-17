package com.m2comm.ultrasound.DTO;

public class CalendarListContentDTO {

    private int sid;
    private String title;

    public CalendarListContentDTO(int sid, String title) {
        this.sid = sid;
        this.title = title;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSid() {
        return sid;
    }

    public String getTitle() {
        return title;
    }

}
