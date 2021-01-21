package com.pis.referralportal.model.request;

import com.pis.referralportal.model.Role;

public class EmployeeRequest {

    private String name;
    private Integer age;
    private String password;
    private String email;
    private String designation;
    private String role;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, String password, Integer age, String email, String designation, String role) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
