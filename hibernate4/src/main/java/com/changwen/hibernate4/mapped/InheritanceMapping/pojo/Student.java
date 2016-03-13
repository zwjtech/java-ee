package com.changwen.hibernate4.mapped.InheritanceMapping.pojo;

/**
 * Student
 *
 * @author lcw 2015/12/23
 */
public class Student {
    private String school;
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }


}
