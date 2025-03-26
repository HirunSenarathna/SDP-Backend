package com.project.sdp.Service;

import com.project.sdp.Entity.Account;

public interface AccountService {
    Account login(String email, String password);
}
