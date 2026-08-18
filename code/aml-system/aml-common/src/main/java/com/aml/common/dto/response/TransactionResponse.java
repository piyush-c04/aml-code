package com.aml.common.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String id;
    private String senderAccountId;
    private String receiverAccountId;
    private BigDecimal amount;
    private String paymentCurrency;
    private String receivedCurrency;
    private LocalDateTime transactionDate;
    private String paymentType;
    private String status;
    private Boolean isFlagged;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
