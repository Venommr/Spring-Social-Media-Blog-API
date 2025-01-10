package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account){
        if(account.getUsername() == null || account.getUsername().isBlank()){
            throw new IllegalArgumentException("Cannot register blank username");
        }

        if(account.getPassword() == null || account.getPassword().length() < 4){
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        if(accountRepository.findByUsername(account.getUsername()) != null){
            throw new IllegalArgumentException("Username is taken already");
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password){
        Account account = accountRepository.findByUsername(username);
        if(account == null || !account.getPassword().equals(password)){
            throw new IllegalArgumentException("Invalid username or password");
        }
        return account;
    }
}
