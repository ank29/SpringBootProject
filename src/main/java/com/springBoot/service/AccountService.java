package com.springBoot.service;

import com.springBoot.entities.Account;
import com.springBoot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Query("select t from UserInfo t where t.userId = ?1")
    public Account getUserByUserId(int userId){

        return accountRepository.findById(userId);
    }


    public List<Account> getUserList()
    {
        return accountRepository.findAll();
    }
}
