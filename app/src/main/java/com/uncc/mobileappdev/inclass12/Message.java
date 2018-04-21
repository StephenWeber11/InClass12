package com.uncc.mobileappdev.inclass12;

import java.util.Date;

/**
 * Created by Stephen on 4/16/2018.
 */

public class Message {
    private String uid;
    private String messageContent;
    private Date date;
    private String fullName;

    public Message(){}

    public Message(String uid, String messageContent, Date date, String fullName) {
        this.uid = uid;
        this.messageContent = messageContent;
        this.date = date;
        this.fullName = fullName;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
