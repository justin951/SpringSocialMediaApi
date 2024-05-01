package com.example.service;

import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, MessageService messageService) {
        this.accountRepository = accountRepository;
    }

    public void register(Account newAccount) {
        accountRepository.save(newAccount);
    }

    public Optional<Account> getAccountByUsername(String username) throws ResourceNotFoundException {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> getAccountById(Integer Id) throws ResourceNotFoundException {
        return accountRepository.findById(Id);
    }

    public Optional<Account> login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

}
