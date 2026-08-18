package com.aml.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "account_hash", nullable = false, unique = true)
    private String accountHash;

    @Column(name = "account_type", length = 50)
    private String accountType;

    @Column(name = "bank_location", length = 100)
    private String bankLocation;

    @Column(name = "account_risk_score")
    private BigDecimal accountRiskScore;

    @Column(name = "avg_transaction_amount")
    private BigDecimal avgTransactionAmount;

    @Column(name = "avg_transactions_per_month")
    private Integer avgTransactionsPerMonth;

    @Column(name = "account_age_days")
    private Integer accountAgeDays;

    @Column(name = "is_flagged")
    private Boolean isFlagged;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isFlagged == null) isFlagged = false;
        if (accountRiskScore == null) accountRiskScore = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
