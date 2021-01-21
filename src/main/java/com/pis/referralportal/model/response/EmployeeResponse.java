package com.pis.referralportal.model.response;

import com.pis.referralportal.model.Role;

public class EmployeeResponse {

    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private String designation;
    private Role role;

    public EmployeeResponse(Integer id, String name, Integer age, String email, String designation, Role role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.designation = designation;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
