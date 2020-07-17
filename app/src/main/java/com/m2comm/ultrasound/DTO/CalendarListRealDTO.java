package com.m2comm.ultrasound.DTO;

public class CalendarListRealDTO {

    private boolean isHeader;
    private int sid;
    private String subject;
    private String period;
    private String place;

    public CalendarListRealDTO(boolean isHeader, int sid, String subject, String period, String place) {
        this.isHeader = isHeader;
        this.sid = sid;
        this.subject = subject;
        this.period = period;
        this.place = place;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public int getSid() {
        return sid;
    }

    public String getSubject() {
        return subject;
    }

    public String getPeriod() {
        return period;
    }

    public String getPlace() {
        return place;
    }
}
