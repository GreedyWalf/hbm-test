package com.qs.entity;

import java.io.Serializable;

public class IdentifyCard implements Serializable {

    private String cardId;
    private String cardName;

    private User user;

    public IdentifyCard(){

    }

    public IdentifyCard(String cardName){
        this.cardName = cardName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
