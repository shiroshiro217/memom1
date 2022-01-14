package com.example.memom.Model;

import java.util.Dictionary;

public class User {
    private String name;
    private String uid;
    private String email;
    private int memoindex;
    private int noteindex;

    public User() {

    }

    public User(String name, String uid, String email, int memoindex, int noteindex) {
        this.email = email;
        this.name = name;
        this.uid = uid;
        this.memoindex = memoindex;
        this.noteindex = noteindex;
    }

    public User(Dictionary dict) {
        this.name = (String) dict.get("name").toString();
        this.uid = (String) dict.get("uid").toString();
        this.email = (String) dict.get("email").toString();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMemoindex() {
        return memoindex;
    }

    public void setMemoindex(int memoindex) {
        this.memoindex = memoindex;
    }

    public int getNoteindex() {
        return noteindex;
    }

    public void setNoteindex(int noteindex) {
        this.noteindex = noteindex;
    }
}

