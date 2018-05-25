package com.qs.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 一的一方
 */
public class Customer implements Serializable {

    private String id;
    private String name;

    //在一的一方维护多的一方数据，这里为一个集合
    private Set<Order> orders;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

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
