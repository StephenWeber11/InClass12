package com.uncc.mobileappdev.inclass12;

/**
 * Created by Stephen on 4/16/2018.
 */

public class Message {
    private String uid;
    private String messageContent;
    private String date;

    public Message(){}

    public Message(String uid, String messageContent, String date) {
        this.uid = uid;
        this.messageContent = messageContent;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
