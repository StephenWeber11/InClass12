package com.uncc.mobileappdev.inclass12;

/**
 * Created by Stephen on 4/16/2018.
 */

public class User {
    private String firstName, lastName, uid, email;

    public User() {}

    public User(String firstName, String lastName, String uid, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
