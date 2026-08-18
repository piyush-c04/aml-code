package com.aml.common.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String id;
    private String accountHash;
    private String accountType;
    private String bankLocation;
    private BigDecimal accountRiskScore;
    private BigDecimal avgTransactionAmount;
    private Integer avgTransactionsPerMonth;
    private Integer accountAgeDays;
    private Boolean isFlagged;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
