package com.pis.referralportal.model.response;

import com.pis.referralportal.model.Employee;
import com.pis.referralportal.model.Job;

import java.util.List;

public class AuthenticationResponse {

    private String jwtToken;
    private EmployeeResponse employee;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public EmployeeResponse getEmployee() {
        return employee;
    }

    public void setEmployeeResponse(EmployeeResponse employee) {
        this.employee = employee;
    }
}
