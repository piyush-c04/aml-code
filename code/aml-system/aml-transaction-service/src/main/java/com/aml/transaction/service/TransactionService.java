package com.aml.transaction.service;

import com.aml.common.dto.request.CreateTransactionRequest;
import com.aml.common.dto.response.TransactionResponse;
import com.aml.common.entity.Account;
import com.aml.common.entity.Transaction;
import com.aml.common.entity.TransactionStatus;
import com.aml.common.repository.AccountRepository;
import com.aml.common.repository.TransactionRepository;
import com.aml.config.event.EventBus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final EventBus eventBus;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            EventBus eventBus
    ) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.eventBus = eventBus;
    }

    // ============================================================
    // CREATE TRANSACTION
    // ============================================================

    @Transactional
    public TransactionResponse createTransaction(
            CreateTransactionRequest request
    ) {

        // --------------------------------------------------------
        // 1. Find sender account
        // --------------------------------------------------------

        Account sender = accountRepository
                .findByAccountHash(
                        hashAccountId(request.getSenderAccountId())
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Sender account not found: "
                                        + request.getSenderAccountId()
                        )
                );

        // --------------------------------------------------------
        // 2. Find receiver account
        // --------------------------------------------------------

        Account receiver = accountRepository
                .findByAccountHash(
                        hashAccountId(request.getReceiverAccountId())
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Receiver account not found: "
                                        + request.getReceiverAccountId()
                        )
                );

        // --------------------------------------------------------
        // 3. Create transaction
        // --------------------------------------------------------

        Transaction transaction = new Transaction();

        transaction.setId(UUID.randomUUID().toString());
        transaction.setTransactionId(request.getTransactionId());
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);
        transaction.setAmount(request.getAmount());
        transaction.setPaymentCurrency(
                request.getPaymentCurrency()
        );
        transaction.setReceivedCurrency(
                request.getReceivedCurrency()
        );
        transaction.setPaymentType(
                request.getPaymentType()
        );
        transaction.setTransactionDatetime(
                request.getTransactionDatetime()
        );

        transaction.setStatus(
                TransactionStatus.COMPLETED
        );

        // --------------------------------------------------------
        // 4. Save
        // --------------------------------------------------------

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        // --------------------------------------------------------
        // 5. Build response
        // --------------------------------------------------------

        TransactionResponse response =
                mapToResponse(savedTransaction);

        // --------------------------------------------------------
        // 6. Publish event
        // --------------------------------------------------------

        eventBus.publish(response);

        return response;
    }

    // ============================================================
    // GET ALL TRANSACTIONS
    // ============================================================

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET BY INTERNAL ID
    // ============================================================

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(
            String id
    ) {

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Transaction not found: " + id
                                )
                        );

        return mapToResponse(transaction);
    }

    // ============================================================
    // GET BY BUSINESS TRANSACTION ID
    // ============================================================

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionByBusinessId(
            String transactionId
    ) {

        Transaction transaction =
                transactionRepository
                        .findByTransactionId(transactionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Transaction not found: "
                                                + transactionId
                                )
                        );

        return mapToResponse(transaction);
    }

    // ============================================================
    // GET TRANSACTIONS FOR ACCOUNT
    // ============================================================

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByAccount(
            String accountId
    ) {

        String accountHash = hashAccountId(accountId);

        Account account = accountRepository
                .findByAccountHash(accountHash)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Account not found: " + accountId
                        )
                );

        return transactionRepository
                .findBySenderAccount_IdOrReceiverAccount_Id(
                        account.getId(),
                        account.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // ENTITY -> RESPONSE
    // ============================================================

    private TransactionResponse mapToResponse(
            Transaction transaction
    ) {

        TransactionResponse response =
                new TransactionResponse();

        response.setId(transaction.getId());

        response.setTransactionId(
                transaction.getTransactionId()
        );

        response.setSenderAccountId(
                transaction.getSenderAccount().getId()
        );

        response.setReceiverAccountId(
                transaction.getReceiverAccount().getId()
        );

        response.setAmount(
                transaction.getAmount()
        );

        response.setPaymentCurrency(
                transaction.getPaymentCurrency()
        );

        response.setReceivedCurrency(
                transaction.getReceivedCurrency()
        );

        response.setTransactionDatetime(
                transaction.getTransactionDatetime()
        );

        response.setPaymentType(
                transaction.getPaymentType()
        );

        response.setStatus(
                transaction.getStatus().name()
        );

        response.setCreatedAt(
                transaction.getCreatedAt()
        );

        return response;
    }

    // ============================================================
    // HASH ACCOUNT ID
    // ============================================================

    private String hashAccountId(String accountId) {
        return com.aml.common.utils.HashUtil.sha256(accountId);
    }
}