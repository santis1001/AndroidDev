package com.example.intracer.Fr_groups;

public class groupmsg implements Comparable<groupmsg>{

    public groupmsg(String key, String email, String msg, String date) {
        this.email = email;
        this.msg = msg;
        this.date = date;
        this.key = key;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int compareTo(groupmsg e) {
    return this.getKey().compareTo(e.getKey());
    }
    String key;
    String email;
    String msg;
    String date;

}
