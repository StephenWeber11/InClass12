package com.uncc.mobileappdev.inclass12;

/**
 * Created by Stephen on 4/16/2018.
 */

public class Message {
    String text;
    int priority;
    String uid;

    public Message() {}

    public Message(String text, int priority, String uid) {
        this.text = text;
        this.priority = priority;
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
