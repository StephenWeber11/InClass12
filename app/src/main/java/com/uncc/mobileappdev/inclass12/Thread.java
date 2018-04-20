package com.uncc.mobileappdev.inclass12;

import java.util.ArrayList;

/**
 * Created by Stephen on 4/16/2018.
 */

public class Thread {
    private String threadName;
    private ArrayList<Message> messages;
    private String uid;
    private String threadID;

    public Thread() {}

    public Thread(String threadName, ArrayList<Message> messages, String uid, String threadID) {
        this.threadName = threadName;
        this.messages = messages;
        this.uid = uid;
        this.threadID = threadID;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }
}
