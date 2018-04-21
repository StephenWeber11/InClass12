package com.uncc.mobileappdev.inclass12;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Stephen on 4/16/2018.
 */

public class Thread {
    private String threadName;
    private HashMap<String, Message> messages;
    private String uid;

    public Thread() {}

    public Thread(String threadName, HashMap<String, Message> messages, String uid) {
        this.threadName = threadName;
        this.messages = messages;
        this.uid = uid;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public HashMap<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, Message> messages) {
        this.messages = messages;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
