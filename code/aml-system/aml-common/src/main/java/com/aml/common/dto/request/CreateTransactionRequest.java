package com.aml.common.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    @NotBlank(message = "Sender account ID is required")
    private String senderAccountId;
    
    @NotBlank(message = "Receiver account ID is required")
    private String receiverAccountId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Payment currency is required")
    @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
    private String paymentCurrency;
    
    @NotBlank(message = "Received currency is required")
    private String receivedCurrency;
    
    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;
    
    @NotBlank(message = "Payment type is required")
    private String paymentType;
}
