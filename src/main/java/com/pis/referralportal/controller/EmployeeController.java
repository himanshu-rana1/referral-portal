package com.pis.referralportal.controller;

import com.pis.referralportal.model.request.EmployeeRequest;
import com.pis.referralportal.model.response.EmployeeResponse;
import com.pis.referralportal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest employeeRequest){
        EmployeeResponse response = employeeService.addEmployee(employeeRequest);
        if(response != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/get/{id}")
    @CrossOrigin
    public ResponseEntity<EmployeeResponse> getEmployeeFromId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeFromId(id));
    }

    @PutMapping("/edit/{id}")
    @CrossOrigin
    public ResponseEntity<EmployeeResponse> editEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok().body(employeeService.editEmployee(id, employeeRequest));
    }

    @DeleteMapping("/delete")
    @CrossOrigin
    public ResponseEntity<EmployeeResponse> deleteEmployee(@RequestParam("id") Integer id) {
        return ResponseEntity.ok().body(employeeService.deleteEmployee(id));
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }
}
