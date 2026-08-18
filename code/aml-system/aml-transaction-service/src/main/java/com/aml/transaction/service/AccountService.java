package com.aml.transaction.service;

import com.aml.common.dto.request.CreateAccountRequest;
import com.aml.common.dto.request.UpdateAccountRequest;
import com.aml.common.dto.response.AccountResponse;
import com.aml.common.entity.Account;
import com.aml.common.repository.AccountRepository;
import com.aml.common.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        String hash = HashUtil.sha256(request.getAccountId());
        if (accountRepository.findByAccountHash(hash).isPresent()) {
            throw new IllegalArgumentException("Account already exists with the given account ID");
        }

        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .accountHash(hash)
                .accountType(request.getAccountType())
                .bankLocation(request.getBankLocation())
                .accountRiskScore(request.getAccountRiskScore())
                .avgTransactionAmount(request.getAvgTransactionAmount())
                .avgTransactionsPerMonth(request.getAvgTransactionsPerMonth())
                .accountAgeDays(request.getAccountAgeDays())
                .isFlagged(request.getIsFlagged())
                .build();

        Account saved = accountRepository.save(account);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountById(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + id));
        return mapToResponse(account);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponse updateAccount(String id, UpdateAccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + id));

        account.setAccountType(request.getAccountType());
        account.setBankLocation(request.getBankLocation());
        account.setAccountRiskScore(request.getAccountRiskScore());
        account.setAvgTransactionAmount(request.getAvgTransactionAmount());
        account.setAvgTransactionsPerMonth(request.getAvgTransactionsPerMonth());
        account.setAccountAgeDays(request.getAccountAgeDays());
        account.setIsFlagged(request.getIsFlagged());

        Account updated = accountRepository.save(account);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteAccount(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + id));
        accountRepository.delete(account);
    }

    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountHash(account.getAccountHash())
                .accountType(account.getAccountType())
                .bankLocation(account.getBankLocation())
                .accountRiskScore(account.getAccountRiskScore())
                .avgTransactionAmount(account.getAvgTransactionAmount())
                .avgTransactionsPerMonth(account.getAvgTransactionsPerMonth())
                .accountAgeDays(account.getAccountAgeDays())
                .isFlagged(account.getIsFlagged())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
