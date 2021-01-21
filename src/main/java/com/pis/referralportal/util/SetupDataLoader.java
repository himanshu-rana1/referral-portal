package com.pis.referralportal.util;

import com.pis.referralportal.model.*;
import com.pis.referralportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("EMPLOYEE_READ");
        Privilege updatePrivilege = createPrivilegeIfNotFound("EMPLOYEE_UPDATE");
        Privilege writePrivilege = createPrivilegeIfNotFound("EMPLOYEE_WRITE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("EMPLOYEE_DELETE");
        Privilege writeJobPrivilege = createPrivilegeIfNotFound("JOB_WRITE");
        Privilege deleteJobPrivilege = createPrivilegeIfNotFound("JOB_DELETE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege,updatePrivilege, writePrivilege, deletePrivilege);
        List<Privilege> hrPrivileges = Arrays.asList(updatePrivilege, writeJobPrivilege, deleteJobPrivilege);
        List<Privilege> employeePrivileges = Arrays.asList(readPrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_EMPLOYEE", employeePrivileges);
        createRoleIfNotFound("ROLE_HR", hrPrivileges);

        addEmployees();
        addJobs();
        addCandidates();
        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByGroupName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String roleName, List<Privilege> privileges) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            role = new Role(roleName, privileges);
            roleRepository.save(role);
        }
        return role;
    }

    private void addEmployees(){
        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
        Employee employee = new Employee();
        employee.setName("Himanshu Rana");
        employee.setPassword(passwordEncoder.encode("password"));
        employee.setAge(25);
        employee.setRole(adminRole);
        employee.setEmail("himanshu@gmail.com");
        employee.setDesignation("Senior Associate");
        employeeRepository.save(employee);

        Role employeeRole = roleRepository.findByRoleName("ROLE_EMPLOYEE");
        Employee employee1 = new Employee();
        employee1.setName("Ravi Kumar");
        employee1.setPassword(passwordEncoder.encode("qwerty"));
        employee1.setAge(30);
        employee1.setRole(employeeRole);
        employee1.setEmail("ravi@gmail.com");
        employee1.setDesignation("Associate");
        employeeRepository.save(employee1);

        Role hrRole = roleRepository.findByRoleName("ROLE_HR");
        Employee employee2 = new Employee();
        employee2.setName("Avinash Pandey");
        employee2.setPassword(passwordEncoder.encode("12345"));
        employee2.setAge(35);
        employee2.setRole(hrRole);
        employee2.setEmail("avinash@gmail.com");
        employee2.setDesignation("Analyst");
        employeeRepository.save(employee2);
    }

    private void addJobs(){
        Job job = new Job("Analyst", "Digital", 3, "Backend Developer. Sound knowledge of Java and Spring boot. Angular is good to have.");
        jobRepository.save(job);

        Job job1 = new Job("Senior Associate", "TFS", 10, "Full Stack Developer. Sound knowledge of Java, Spring boot, Design Patterns and Data Structure. Angular is must to have.");
        jobRepository.save(job1);

        Job job2 = new Job("Senior Analyst", "Devops", 5, "Devops Engineer. Sound knowledge of AWS and Jenkins. Git Integration is good to have.");
        jobRepository.save(job2);

        Job job3 = new Job("Associate", "HR", 7, "HR Manager. Good communication skills and managing qualities. Certificate is good to have.");
        jobRepository.save(job3);
    }

    private void addCandidates(){
        Candidate candidate = new Candidate("Kavish Reddy", 5, 3, "Himanshu Rana", 1, "HIRED", new byte[0]);
        candidateRepository.save(candidate);
        Candidate candidate1 = new Candidate("Akansha Verma", 8, 4, "Ravi Kumar", 2, "HIRED", new byte[0]);
        candidateRepository.save(candidate1);
        Candidate candidate2 = new Candidate("Ramesh Solanki", 4, 3, "Avinash Pandey", 3, "REJECT", new byte[0]);
        candidateRepository.save(candidate2);
    }
}