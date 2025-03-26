package com.project.sdp.Service.impl;

import com.project.sdp.Dto.CustomerDTO;
import com.project.sdp.Entity.Account;
import com.project.sdp.Entity.Customer;
import com.project.sdp.Repo.AccountRepository;
import com.project.sdp.Repo.CustomerRepo;
import com.project.sdp.Service.CustomerService;
import com.project.sdp.shared.ApplicationConstants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public String addCustomer(CustomerDTO customerDTO) {

        if(accountRepository.existsByEmail(customerDTO.getEmail())){
            throw new RuntimeException("Customer already exists with this email: " + customerDTO.getEmail());
        }
        Account account = new Account(
                customerDTO.getEmail(),
                this.passwordEncoder.encode(customerDTO.getPassword()),
                ApplicationConstants.ROLES.ROLE_CUSTOMER.name()
        );

        Account savedAccount = accountRepository.save(account);

        Customer customer = new Customer(
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getAddress(),
                customerDTO.getPhone()
        );

        System.out.println(customerDTO.getFirstName() + " " + customerDTO.getLastName());

        customer.setAccount(savedAccount);
        customerRepo.save(customer);

        return "Registered a Customer Successfully";

    }
}
