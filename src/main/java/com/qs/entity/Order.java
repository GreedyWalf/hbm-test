package com.qs.entity;

import java.io.Serializable;

/**
 * 多的一方
 */
public class Order implements Serializable {

    private String orderId;
    private String orderNumber;

    //多对一，每个order对象都会拥有一个customer对象
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order() {
    }

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
