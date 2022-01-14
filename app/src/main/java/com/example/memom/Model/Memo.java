package com.example.memom.Model;

import java.util.Date;

public class Memo {
    private int id;
    private String event;
//    private Boolean status;
//    private Date notifyTime;

    public Memo(){
        super();
    }

    public Memo(int id, String event){
        this.id = id;
        this.event = event;
//        this.status = status;
//        this.notifyTime = notifyTime;
    }

    public int getId() {
        return id;
    }

//    public Boolean getStatus() {
//        return status;
//    }

//    public Date getNotifyTime() {
//        return notifyTime;
//    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public void setNotifyTime(Date notifyTime) {
//        this.notifyTime = notifyTime;
//    }

//    public void setStatus(Boolean status) {
//        this.status = status;
//    }
}
