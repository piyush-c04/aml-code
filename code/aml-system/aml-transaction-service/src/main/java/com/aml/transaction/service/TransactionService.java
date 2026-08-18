package com.aml.transaction.service;

import com.aml.common.dto.request.CreateTransactionRequest;
import com.aml.common.dto.response.TransactionResponse;
import com.aml.common.entity.Account;
import com.aml.common.entity.Transaction;
import com.aml.common.repository.AccountRepository;
import com.aml.common.repository.TransactionRepository;
import com.aml.common.utils.HashUtil;
import com.aml.config.event.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final EventBus eventBus;

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        // Retrieve or create hashed sender account
        String senderHash = HashUtil.sha256(request.getSenderAccountId());
        Account sender = accountRepository.findByAccountHash(senderHash)
                .orElseGet(() -> accountRepository.save(Account.builder()
                        .id(UUID.randomUUID().toString())
                        .accountHash(senderHash)
                        .accountType("SAVINGS")
                        .bankLocation("US")
                        .accountRiskScore(BigDecimal.ZERO)
                        .isFlagged(false)
                        .build()));

        // Retrieve or create hashed receiver account
        String receiverHash = HashUtil.sha256(request.getReceiverAccountId());
        Account receiver = accountRepository.findByAccountHash(receiverHash)
                .orElseGet(() -> accountRepository.save(Account.builder()
                        .id(UUID.randomUUID().toString())
                        .accountHash(receiverHash)
                        .accountType("SAVINGS")
                        .bankLocation("US")
                        .accountRiskScore(BigDecimal.ZERO)
                        .isFlagged(false)
                        .build()));

        // Map and save transaction
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .senderAccount(sender)
                .receiverAccount(receiver)
                .amount(request.getAmount())
                .paymentCurrency(request.getPaymentCurrency())
                .receivedCurrency(request.getReceivedCurrency())
                .transactionDate(request.getTransactionDate())
                .paymentType(request.getPaymentType())
                .status("COMPLETED")
                .isFlagged(false)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Map response
        TransactionResponse response = TransactionResponse.builder()
                .id(savedTransaction.getId())
                .senderAccountId(sender.getId())
                .receiverAccountId(receiver.getId())
                .amount(savedTransaction.getAmount())
                .paymentCurrency(savedTransaction.getPaymentCurrency())
                .receivedCurrency(savedTransaction.getReceivedCurrency())
                .transactionDate(savedTransaction.getTransactionDate())
                .paymentType(savedTransaction.getPaymentType())
                .status(savedTransaction.getStatus())
                .isFlagged(savedTransaction.getIsFlagged())
                .createdAt(savedTransaction.getCreatedAt())
                .build();

        // Publish event to the in-memory event bus
        eventBus.publish(response);

        return response;
    }
}
