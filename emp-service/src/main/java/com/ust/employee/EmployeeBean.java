package com.ust.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import reactor.core.publisher.Mono;

public class EmployeeBean {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("email_id")
    private String email;

    @JsonProperty("department_id")
    private Long deptId;

    @JsonProperty("department")
    private DepartmentBean department;

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        this.department = department;
    }

    public EmployeeBean(String id, String name, String email, Long deptId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deptId = deptId;
    }

    public EmployeeBean() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
