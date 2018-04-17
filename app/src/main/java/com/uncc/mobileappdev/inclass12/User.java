package com.uncc.mobileappdev.inclass12;

/**
 * Created by Stephen on 4/16/2018.
 */

public class User {
    private String firstName, lastName, uid;

    public User() {}

    public User(String firstName, String lastName, String uid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
