package com.changwen.springdata.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Person
 *
 * @author lcw 2015/12/20
 */
@Table(name="jpa_personsTest")
@Entity
public class PersonTest {
    private Integer id;
    private String lastName;
    private String email;
    private Date birth;

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
}

