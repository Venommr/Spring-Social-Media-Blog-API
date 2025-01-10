package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;
//import com.example.entity.Message;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    Account findByUsername(String username);
}
