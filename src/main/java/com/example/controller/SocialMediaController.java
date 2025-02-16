package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
@RequestMapping
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // TODO 1: PARTIAL
    @PostMapping("register")
    public ResponseEntity<String> registerAccount(@RequestBody Account newAccount) {
        if (newAccount.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username must not be blank");
        }

        if (newAccount.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be at least 4 characters long");
        }

        Optional<Account> preexistingAccount = accountService.getAccountByUsername(newAccount.getUsername());

        if (preexistingAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        } else {
            try {
                accountService.register(newAccount);
                return ResponseEntity.ok() // should be 200?
                        .body("Account successfully registered");
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Account registration failed: " + ex.getMessage());
            }
        }

    }

    // TODO 2: PARTIAL
    @PostMapping("login")
    public ResponseEntity<?> loginAccount(@RequestBody Account account) throws AuthenticationException {
        Optional<Account> loggedAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loggedAccount.isPresent()) {
            return ResponseEntity.ok(loggedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    // TODO 3: PARTIAL
    @PostMapping("messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message newMessage) {

        if (newMessage.getMessageText() == null || newMessage.getMessageText().isEmpty()
                || newMessage.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        if (accountService.getAccountById(newMessage.getPostedBy()).isPresent()) {
            messageService.addMessage(newMessage);
            return ResponseEntity.ok()
                    .body(newMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

    }

    // TODO 4: PARTIAL
    @GetMapping("messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessagesList = messageService.getMessages();
        return ResponseEntity.ok().body(allMessagesList);
    }

    // TODO 5: PARTIAL
    @GetMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Optional<Message>> getMessage(@PathVariable Integer messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.isPresent() ? ResponseEntity.ok().body(message) : ResponseEntity.ok().build();
    }

    // TODO 6: PARTIAL
    @DeleteMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<String> deleteMessage(@PathVariable Integer messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        return deleted ? ResponseEntity.ok().body("1") : ResponseEntity.ok().build();
    }

    // TODO 7: PARTIAL
    @PatchMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<String> patchMessage(
            @PathVariable Integer messageId,
            @RequestBody Map<String, String> requestBody) {
        String messageText = requestBody.get("messageText");
        Long timePostedEpoch = requestBody.get("timePostedEpoch") != null ?
                Long.parseLong(requestBody.get("timePostedEpoch").toString()) : 0L;

        if (messageText.isEmpty() || messageText.length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        try {
            boolean patched = messageService.patchMessage(messageId, messageText, timePostedEpoch);
            if (patched) {
                return ResponseEntity.ok().body("1");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    // TODO 8: PARTIAL
    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesByPostedBy(@PathVariable int accountId) {
        List<Message> messagesByAccount = messageService.getMessagesByPostedBy(accountId);
        return ResponseEntity.ok()
                .body(messagesByAccount);
    }

}