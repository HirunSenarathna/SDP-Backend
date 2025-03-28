package com.project.sdp.Service.impl;

import com.project.sdp.Dto.EmployeeDTO;
import com.project.sdp.Entity.Account;
import com.project.sdp.Entity.Employee;
import com.project.sdp.Repo.AccountRepository;
import com.project.sdp.Repo.EmployeeRepository;
import com.project.sdp.Service.EmployeeService;
import com.project.sdp.shared.ApplicationConstants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public String addEmployee(EmployeeDTO employeeDTO) {

        if(accountRepository.existsByEmail(employeeDTO.getEmail())){
            throw new RuntimeException("Employee already exists with this email: " + employeeDTO.getEmail());
        }

        String role;
        switch (employeeDTO.getRole().toUpperCase()) {
            case "WAITER":
                role = ApplicationConstants.ROLES.ROLE_WAITER.name();
                break;
            case "CASHIER":
                role = ApplicationConstants.ROLES.ROLE_CASHIER.name();
                break;
            default:
                throw new IllegalArgumentException("Invalid role provided: " + employeeDTO.getRole());
        }

        Account account = new Account(
                employeeDTO.getEmail(),
                this.passwordEncoder.encode(employeeDTO.getPassword()),
                role
        );

        Account savedAccount = accountRepository.save(account);

        Employee employee = new Employee(
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getAddress(),
                employeeDTO.getPhone()
        );

        System.out.println(employeeDTO.getFirstName() + " " + employeeDTO.getLastName());

        employee.setAccount(savedAccount);
        employeeRepository.save(employee);

        return "Registered a Employee Successfully";

    }
}
