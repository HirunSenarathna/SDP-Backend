package com.project.sdp.Service.impl;

import com.project.sdp.Entity.Account;
import com.project.sdp.Repo.AccountRepository;
import com.project.sdp.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account login(String email, String password) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            System.out.println("Found account for email: " + email);
            System.out.println("🔹 Found account: " + account.getEmail());
            System.out.println("🔹 Stored Hashed Password: " + account.getPassword());
            System.out.println("🔹 Entered Raw Password: " + password);

            // Compare raw password with hashed password
            if (passwordEncoder.matches(password, account.getPassword())) {
                System.out.println("✅ Password Matched!");
                return account;
            } else {
                System.out.println("Password mismatch for email: " + email);
            }
        } else {
            System.out.println("No account found for email: " + email);
        }

        throw new RuntimeException("Invalid email or password");
    }
}

