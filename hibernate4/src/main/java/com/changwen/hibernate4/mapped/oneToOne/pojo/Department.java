package com.changwen.hibernate4.mapped.oneToOne.pojo;

/**
 * Department
 *
 * @author lcw 2015/12/23
 */
public class Department {
    private Integer deptId;
    private String deptName;
    private Manager mgr;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Manager getMgr() {
        return mgr;
    }

    public void setMgr(Manager mgr) {
        this.mgr = mgr;
    }



}

