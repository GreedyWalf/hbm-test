package com.qs.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 一的一方
 */
public class Customer implements Serializable {

    private String customerId;
    private String customerName;

    //一的一方维护多的一方数据（set集合）
    private Set<Order> orderSet;

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    public Customer() {
    }

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
