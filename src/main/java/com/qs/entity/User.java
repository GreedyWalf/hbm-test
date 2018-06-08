package com.qs.entity;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String userName;


    public User(String userName) {
        this.userName = userName;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
