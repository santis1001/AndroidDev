package com.example.intracer.Fr_groups;

public class NewGroup {

    String message;
    String datetime;
    String sendermail;
    String receivermail;

    public NewGroup(String sendermail, String receivermail ,String message, String datetime) {
        this.message = message;
        this.datetime = datetime;
        this.sendermail = sendermail;
        this.receivermail = receivermail;
    }
}
