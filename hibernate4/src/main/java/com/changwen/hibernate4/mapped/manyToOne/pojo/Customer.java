package com.changwen.hibernate4.mapped.manyToOne.pojo;

/**
 * Customer
 *
 * @author lcw 2015/12/23
 */
public class Customer {
    private Integer customerId;
    private String customerName;

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
}
