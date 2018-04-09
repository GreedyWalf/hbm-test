package com.qs.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Customer implements Serializable {

    private String id;
    private String name;

    public Customer(){}

    public Customer(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
