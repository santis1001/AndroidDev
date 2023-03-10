package com.example.intracer.Fr_chat;

public class Message {

    String message;
    String datetime;
    String senderuid;
    String receiveruid;
    String sendermail;
    String receivermail;

    public Message(String sendermail, String receivermail ,String message, String datetime) {
        this.message = message;
        this.datetime = datetime;
        this.sendermail = sendermail;
        this.receivermail = receivermail;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSenderuid() {
        return senderuid;
    }

    public void setSenderuid(String senderuid) {
        this.senderuid = senderuid;
    }

    public String getReceiveruid() {
        return receiveruid;
    }

    public void setReceiveruid(String receiveruid) {
        this.receiveruid = receiveruid;
    }

    public String getSendermail() {
        return sendermail;
    }

    public void setSendermail(String sendermail) {
        this.sendermail = sendermail;
    }

    public String getReceivermail() {
        return receivermail;
    }

    public void setReceivermail(String receivermail) {
        this.receivermail = receivermail;
    }

}
