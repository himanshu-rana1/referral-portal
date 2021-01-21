package com.pis.referralportal.service;

import com.pis.referralportal.exception.EmployeeNotFoundException;
import com.pis.referralportal.model.Employee;
import com.pis.referralportal.model.Role;
import com.pis.referralportal.model.request.EmployeeRequest;
import com.pis.referralportal.model.response.EmployeeResponse;
import com.pis.referralportal.repository.EmployeeRepository;
import com.pis.referralportal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    private final String ADMIN_ROLE = "ROLE_ADMIN";


    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest){
        Employee emp = employeeRepository.findByEmail(employeeRequest.getEmail());
        Role role = roleRepository.findByRoleName(employeeRequest.getRole());
        if(emp != null || role == null){
            return null;
        }
        Employee employee = new Employee(employeeRequest.getName(), encoder.encode(employeeRequest.getPassword()), employeeRequest.getAge(), employeeRequest.getEmail(), employeeRequest.getDesignation(), role);
        Employee e = employeeRepository.save(employee);
        return new EmployeeResponse(e.getEmployeeId(), e.getName(), e.getAge(), e.getEmail(), e.getDesignation(), e.getRole());
    }

    public EmployeeResponse getEmployeeFromId(Integer id)throws EmployeeNotFoundException{
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee == null) {
            throw new EmployeeNotFoundException("Employee doesn't exist with id : " + id);
        }else {
            return new EmployeeResponse(employee.getEmployeeId(), employee.getName(), employee.getAge(), employee.getEmail(), employee.getDesignation(), employee.getRole());
        }
    }

    public List<EmployeeResponse> getAllEmployees(){
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        return employees.stream()
                .map(emp -> new EmployeeResponse(emp.getEmployeeId(), emp.getName(),emp.getAge(), emp.getEmail(), emp.getDesignation(), emp.getRole()))
                .collect(Collectors.toList());
    }

    public EmployeeResponse editEmployee(Integer id, EmployeeRequest employeeRequest){
        Employee employee =  employeeRepository.findById(id).orElse(null);
        if(employee != null){
            if(employeeRequest.getAge() != null){
                employee.setAge(employeeRequest.getAge());
            }
            if(employeeRequest.getEmail() != null){
                employee.setEmail(employeeRequest.getEmail());
            }
            if(employeeRequest.getName() != null){
                employee.setName(employeeRequest.getName());
            }
            if(employeeRequest.getPassword()!= null){
                employee.setPassword(encoder.encode(employeeRequest.getPassword()));
            }
            if(employeeRequest.getDesignation()!=null){
                employee.setDesignation(employeeRequest.getDesignation());
            }
            Employee data = employeeRepository.save(employee);
            return new EmployeeResponse(data.getEmployeeId(), data.getName(), data.getAge(), data.getEmail(), data.getDesignation(), data.getRole());
        }
        throw new EmployeeNotFoundException("Employee doesn't exist with id : " + id);
    }

    public EmployeeResponse deleteEmployee(Integer id){
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee == null) {
            throw new EmployeeNotFoundException("Employee doesn't exist with id : " + id);
        }else {
            employeeRepository.deleteById(id);
            return new EmployeeResponse(employee.getEmployeeId(), employee.getName(), employee.getAge(), employee.getEmail(), employee.getDesignation(), employee.getRole());
        }
    }

}
