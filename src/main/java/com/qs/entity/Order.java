package com.qs.entity;

import java.io.Serializable;

public class Order implements Serializable {

    private String id;
    private String orderNumber;

    //多对一，每个order对象都会拥有一个customer对象
    private Customer customer;

    public Order(){}

    public Order(String orderNumber, Customer customer) {
        this.orderNumber = orderNumber;
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
