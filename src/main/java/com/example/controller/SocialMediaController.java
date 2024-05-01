package com.example.controller;


import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("placeholder")
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody Account account) {
        if (account.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username must not be blank");
        }

        if (account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be at least 4 characters long");
        }

        Optional<Account> preexistingAccount = accountService.getAccountByUsername(account.getUsername());

        if (preexistingAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        } else {
            try {
                accountService.register(account);
                return ResponseEntity.ok() // should be 200?
                        .body("Account successfully registered");
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Account registration failed: " + ex.getMessage());
            }
        }

    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Account account) throws AuthenticationException {
        Optional<Account> loggedAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loggedAccount.isPresent()) {
            return ResponseEntity.ok(loggedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }



}
