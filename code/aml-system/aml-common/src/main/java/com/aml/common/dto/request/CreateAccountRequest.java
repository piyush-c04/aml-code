package com.aml.common.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    @NotBlank(message = "Account ID is required")
    private String accountId;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotBlank(message = "Bank location is required")
    private String bankLocation;

    private BigDecimal accountRiskScore;
    private BigDecimal avgTransactionAmount;
    private Integer avgTransactionsPerMonth;
    private Integer accountAgeDays;
    private Boolean isFlagged;
}
