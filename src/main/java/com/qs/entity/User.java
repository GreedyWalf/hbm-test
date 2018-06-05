package com.qs.entity;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String userName;

    //User一对一关联IdentifyCard
    private IdentifyCard identifyCard;

    public User(String userName) {
        this.userName = userName;
    }

    public User() {

    }

    public IdentifyCard getIdentifyCard() {
        return identifyCard;
    }

    public void setIdentifyCard(IdentifyCard identifyCard) {
        this.identifyCard = identifyCard;
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
