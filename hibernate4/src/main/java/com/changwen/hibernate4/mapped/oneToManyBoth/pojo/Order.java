package com.changwen.hibernate4.mapped.oneToManyBoth.pojo;

/**
 * Order
 *
 * @author lcw 2015/12/23
 */
public class Order {
    private Integer orderId;
    private String orderName;
    private Customer customer;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
