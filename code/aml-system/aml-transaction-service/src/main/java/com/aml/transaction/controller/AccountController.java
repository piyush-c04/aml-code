package com.aml.transaction.controller;

import com.aml.common.dto.common.ApiResponse;
import com.aml.common.dto.request.CreateAccountRequest;
import com.aml.common.dto.request.UpdateAccountRequest;
import com.aml.common.dto.response.AccountResponse;
import com.aml.transaction.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Account created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable String id) {
        AccountResponse response = accountService.getAccountById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Account retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        List<AccountResponse> response = accountService.getAllAccounts();
        return ResponseEntity.ok(ApiResponse.success(response, "Accounts retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @PathVariable String id,
            @Valid @RequestBody UpdateAccountRequest request) {
        AccountResponse response = accountService.updateAccount(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Account updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Account deleted successfully"));
    }
}
