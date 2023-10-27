package com.ust.department;

public class DepartmentBean {
    private Long deptId;

    private String deptName;

    private String deptCode;


    public DepartmentBean(long deptId, String deptName, String deptCode) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptCode = deptCode;
    }

    public DepartmentBean() {
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    @Override
    public String toString() {
        return "{" + "deptId=" + deptId + ", deptName='" + deptName + ", deptCode='" + deptCode + "}";
    }
}