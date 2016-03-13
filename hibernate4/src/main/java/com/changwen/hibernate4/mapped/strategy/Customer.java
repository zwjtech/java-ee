package com.changwen.hibernate4.mapped.strategy;

import java.util.HashSet;
import java.util.Set;

/**
 * Customer
 *
 * @author lcw 2015/12/23
 */
public class Customer {
    private Integer customerId;
    private String customerName;

    private Set<Order> orders = new HashSet<Order>();

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }



}