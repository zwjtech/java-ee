package com.changwen.springdata.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Person
 *
 * @author lcw 2015/12/20
 */
@Table(name="jpa_persons")
@Entity
public class Person {

    private Integer id;
    private String lastName;

    private String email;
    private Date birth;

    private Address address;

    private Integer addressId;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Column(name="add_id")
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @JoinColumn(name="address_id")
    @ManyToOne
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", lastName=" + lastName + ", email="
                + email + ", brith=" + birth + "]";
    }
}

