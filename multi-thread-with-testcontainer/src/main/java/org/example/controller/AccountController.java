package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.rest.AccountRest;
import org.example.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account")
    public ResponseEntity<List<Long>> getAccount() {
        return ResponseEntity.ok(accountService.getAllIds());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<AccountRest> getAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.get(accountId));
    }

    @PostMapping("/account")
    public ResponseEntity<AccountRest> postAccount(@RequestBody AccountRest rest) {
        return ResponseEntity.ok(accountService.post(rest));
    }
}
