package com.pis.referralportal.repository;

import com.pis.referralportal.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    public Employee findByName(String name);
    public Employee findByEmail(String email);
}
