package com.changwen.hibernate4.mapped.joined.subclass;

/**
 * Student
 *
 * @author lcw 2015/12/23
 */
public class Student extends Person{

    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
