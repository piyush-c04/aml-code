package com.aml.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id", nullable = false)
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_id", nullable = false)
    private Account receiverAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_currency", length = 10)
    private String paymentCurrency;

    @Column(name = "received_currency", length = 10)
    private String receivedCurrency;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "payment_type", length = 50)
    private String paymentType;

    @Column(length = 50)
    private String status;

    @Column(name = "is_flagged")
    private Boolean isFlagged;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (isFlagged == null) isFlagged = false;
        if (status == null) status = "PENDING";
        if (transactionDate == null) transactionDate = LocalDateTime.now();
    }
}
