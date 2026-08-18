package com.aml.transaction.service;

import com.aml.common.dto.request.CreateTransactionRequest;
import com.aml.common.dto.response.TransactionResponse;
import com.aml.common.entity.Account;
import com.aml.common.entity.Customer;
import com.aml.common.entity.Transaction;
import com.aml.common.entity.TransactionStatus;
import com.aml.common.repository.AccountRepository;
import com.aml.common.repository.CustomerRepository;
import com.aml.common.repository.TransactionRepository;
import com.aml.common.utils.HashUtil;
import com.aml.config.event.EventBus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final EventBus eventBus;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            EventBus eventBus
    ) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
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
        // 1. Retrieve or create sender account
        // --------------------------------------------------------
        String senderHash = HashUtil.sha256(
                request.getSenderAccountId()
        );

        Account sender = accountRepository
                .findByAccountHash(senderHash)
                .orElseGet(() -> createAccount(senderHash));

        // --------------------------------------------------------
        // 2. Retrieve or create receiver account
        // --------------------------------------------------------
        String receiverHash = HashUtil.sha256(
                request.getReceiverAccountId()
        );

        Account receiver = accountRepository
                .findByAccountHash(receiverHash)
                .orElseGet(() -> createAccount(receiverHash));

        // --------------------------------------------------------
        // 3. Create transaction
        // --------------------------------------------------------
        Transaction transaction = new Transaction();

        transaction.setId(UUID.randomUUID().toString());

        transaction.setTransactionId(
                request.getTransactionId() != null
                        ? request.getTransactionId()
                        : "TXN-" + UUID.randomUUID()
        );

        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);

        transaction.setAmount(request.getAmount());
        transaction.setPaymentCurrency(
                request.getPaymentCurrency()
        );
        transaction.setReceivedCurrency(
                request.getReceivedCurrency()
        );

        transaction.setTransactionDatetime(
                request.getTransactionDatetime() != null
                        ? request.getTransactionDatetime()
                        : OffsetDateTime.now()
        );

        transaction.setPaymentType(
                request.getPaymentType()
        );

        transaction.setStatus(
                TransactionStatus.COMPLETED
        );

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        // --------------------------------------------------------
        // 4. Build response
        // --------------------------------------------------------
        TransactionResponse response =
                mapToResponse(savedTransaction);

        // --------------------------------------------------------
        // 5. Publish transaction event
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
    // GET TRANSACTION BY INTERNAL DATABASE ID
    // ============================================================

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(
            String id
    ) {

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Transaction not found with ID: "
                                                + id
                                )
                        );

        return mapToResponse(transaction);
    }

    // ============================================================
    // GET TRANSACTION BY BUSINESS TRANSACTION ID
    // ============================================================

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionByBusinessId(
            String transactionId
    ) {

        Transaction transaction =
                transactionRepository.findByTransactionId(
                        transactionId
                ).orElseThrow(() ->
                        new IllegalArgumentException(
                                "Transaction not found: "
                                        + transactionId
                        )
                );

        return mapToResponse(transaction);
    }

    // ============================================================
    // GET TRANSACTIONS FOR AN ACCOUNT
    // ============================================================

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByAccount(
            String accountId
    ) {

        List<Transaction> transactions =
                transactionRepository
                        .findBySenderAccount_IdOrReceiverAccount_Id(
                                accountId,
                                accountId
                        );

        return transactions
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // MAP ENTITY -> RESPONSE
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
    // TEMPORARY ACCOUNT CREATION
    // ============================================================

    /**
     * Temporary account creation logic.
     *
     * This should eventually move to AccountService once
     * transaction creation receives real customer/account
     * information.
     */
    private Account createAccount(String accountHash) {

        // Create temporary customer
        Customer customer = new Customer();

        customer.setId(
                UUID.randomUUID().toString()
        );

        customer.setAccountHolderType(
                "INDIVIDUAL"
        );

        customer.setKycVerificationStatus(
                "PENDING"
        );

        customer.setRiskCountryFlag(false);

        Customer savedCustomer =
                customerRepository.save(customer);

        // Create account
        Account account = new Account();

        account.setId(
                UUID.randomUUID().toString()
        );

        account.setCustomer(
                savedCustomer
        );

        account.setAccountHash(
                accountHash
        );

        account.setAccountType(
                "SAVINGS"
        );

        account.setBankLocation(
                "US"
        );

        account.setAccountRiskScore(
                BigDecimal.ZERO
        );

        account.setIsFlagged(false);

        return accountRepository.save(account);
    }
}