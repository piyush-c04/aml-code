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
import java.util.UUID;

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

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        // ------------------------------------------------------------
        // 1. Retrieve or create sender account
        // ------------------------------------------------------------
        String senderHash = HashUtil.sha256(request.getSenderAccountId());

        Account sender = accountRepository.findByAccountHash(senderHash)
                .orElseGet(() -> createAccount(senderHash));

        // ------------------------------------------------------------
        // 2. Retrieve or create receiver account
        // ------------------------------------------------------------
        String receiverHash = HashUtil.sha256(request.getReceiverAccountId());

        Account receiver = accountRepository.findByAccountHash(receiverHash)
                .orElseGet(() -> createAccount(receiverHash));

        // ------------------------------------------------------------
        // 3. Create transaction
        // ------------------------------------------------------------
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
        transaction.setPaymentCurrency(request.getPaymentCurrency());
        transaction.setReceivedCurrency(request.getReceivedCurrency());

        transaction.setTransactionDatetime(
                request.getTransactionDatetime() != null
                        ? request.getTransactionDatetime()
                        : OffsetDateTime.now()
        );

        transaction.setPaymentType(request.getPaymentType());
        transaction.setStatus(TransactionStatus.COMPLETED);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        // ------------------------------------------------------------
        // 4. Build response
        // ------------------------------------------------------------
        TransactionResponse response = new TransactionResponse();

        response.setId(savedTransaction.getId());
        response.setTransactionId(savedTransaction.getTransactionId());
        response.setSenderAccountId(sender.getId());
        response.setReceiverAccountId(receiver.getId());
        response.setAmount(savedTransaction.getAmount());
        response.setPaymentCurrency(savedTransaction.getPaymentCurrency());
        response.setReceivedCurrency(savedTransaction.getReceivedCurrency());
        response.setTransactionDatetime(
                savedTransaction.getTransactionDatetime()
        );
        response.setPaymentType(savedTransaction.getPaymentType());
        response.setStatus(savedTransaction.getStatus().name());
        response.setCreatedAt(savedTransaction.getCreatedAt());

        // ------------------------------------------------------------
        // 5. Publish transaction event
        // ------------------------------------------------------------
        eventBus.publish(response);

        return response;
    }

    /**
     * Temporary account creation logic.
     *
     * This will later move into AccountService once the proper
     * customer/account API is finalized.
     */
    private Account createAccount(String accountHash) {

        // Create a temporary customer.
        Customer customer = new Customer();

        customer.setId(UUID.randomUUID().toString());
        customer.setAccountHolderType("INDIVIDUAL");
        customer.setKycVerificationStatus("PENDING");
        customer.setRiskCountryFlag(false);

        Customer savedCustomer = customerRepository.save(customer);

        // Create the account.
        Account account = new Account();

        account.setId(UUID.randomUUID().toString());
        account.setCustomer(savedCustomer);
        account.setAccountHash(accountHash);
        account.setAccountType("SAVINGS");
        account.setBankLocation("US");
        account.setAccountRiskScore(BigDecimal.ZERO);
        account.setIsFlagged(false);

        return accountRepository.save(account);
    }
}