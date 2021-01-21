package com.pis.referralportal.service;

import com.pis.referralportal.model.Employee;
import com.pis.referralportal.model.Job;
import com.pis.referralportal.model.response.AuthenticationResponse;
import com.pis.referralportal.model.response.EmployeeResponse;
import com.pis.referralportal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public AuthenticationResponse getAuthResponse(String token, String email){
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setJwtToken(token);
        Employee employee = employeeRepository.findByEmail(email);
        EmployeeResponse response = new EmployeeResponse(employee.getEmployeeId(), employee.getName(), employee.getAge(), employee.getEmail(), employee.getDesignation(), employee.getRole());
        response.setRole(employee.getRole());
        authResponse.setEmployeeResponse(response);
        return authResponse;
    }
}
