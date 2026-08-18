package com.aml.common.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class CreateTransactionRequest {

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotBlank(message = "Sender account ID is required")
    private String senderAccountId;

    @NotBlank(message = "Receiver account ID is required")
    private String receiverAccountId;

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Amount must be greater than zero"
    )
    private BigDecimal amount;

    @NotBlank(message = "Payment currency is required")
    private String paymentCurrency;

    @NotBlank(message = "Received currency is required")
    private String receivedCurrency;

    @NotBlank(message = "Payment type is required")
    private String paymentType;

    @NotNull(message = "Transaction datetime is required")
    private OffsetDateTime transactionDatetime;

    public CreateTransactionRequest() {
    }

    public CreateTransactionRequest(
            String transactionId,
            String senderAccountId,
            String receiverAccountId,
            BigDecimal amount,
            String paymentCurrency,
            String receivedCurrency,
            String paymentType,
            OffsetDateTime transactionDatetime
    ) {
        this.transactionId = transactionId;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.paymentCurrency = paymentCurrency;
        this.receivedCurrency = receivedCurrency;
        this.paymentType = paymentType;
        this.transactionDatetime = transactionDatetime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getReceivedCurrency() {
        return receivedCurrency;
    }

    public void setReceivedCurrency(String receivedCurrency) {
        this.receivedCurrency = receivedCurrency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public OffsetDateTime getTransactionDatetime() {
        return transactionDatetime;
    }

    public void setTransactionDatetime(
            OffsetDateTime transactionDatetime
    ) {
        this.transactionDatetime = transactionDatetime;
    }
}