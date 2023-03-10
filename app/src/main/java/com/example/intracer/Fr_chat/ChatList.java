package com.example.intracer.Fr_chat;

public class ChatList {
    private String userID;
    private String username;
    private String usermail;
   // private String urlProfile;

    public ChatList(String userID, String username, String usermail/*, String urlProfile*/ ){
        this.userID = userID;
        this.username = username;
        this.usermail = usermail;
        //this.urlProfile = urlProfile;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

//    public String getUrlProfile() {
//        return urlProfile;
//    }
//
//    public void setUrlProfile(String urlProfile) {
//        this.urlProfile = urlProfile;
//    }

    public ChatList(String userID) {
        this.userID = userID;
    }

}
