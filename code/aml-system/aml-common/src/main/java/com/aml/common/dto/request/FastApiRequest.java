package com.aml.common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class FastApiRequest {

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("sender_account")
    private String senderAccount;

    @JsonProperty("receiver_account")
    private String receiverAccount;

    private BigDecimal amount;

    @JsonProperty("payment_currency")
    private String paymentCurrency;

    @JsonProperty("received_currency")
    private String receivedCurrency;

    @JsonProperty("sender_bank_location")
    private String senderBankLocation;

    @JsonProperty("receiver_bank_location")
    private String receiverBankLocation;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("transaction_datetime")
    private OffsetDateTime transactionDatetime;

    @JsonProperty("sender_history")
    private HistoryDto senderHistory;

    @JsonProperty("receiver_history")
    private HistoryDto receiverHistory;

    @JsonProperty("use_gemini")
    private Boolean useGemini;

    public FastApiRequest() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
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

    public String getSenderBankLocation() {
        return senderBankLocation;
    }

    public void setSenderBankLocation(String senderBankLocation) {
        this.senderBankLocation = senderBankLocation;
    }

    public String getReceiverBankLocation() {
        return receiverBankLocation;
    }

    public void setReceiverBankLocation(String receiverBankLocation) {
        this.receiverBankLocation = receiverBankLocation;
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

    public HistoryDto getSenderHistory() {
        return senderHistory;
    }

    public void setSenderHistory(HistoryDto senderHistory) {
        this.senderHistory = senderHistory;
    }

    public HistoryDto getReceiverHistory() {
        return receiverHistory;
    }

    public void setReceiverHistory(HistoryDto receiverHistory) {
        this.receiverHistory = receiverHistory;
    }

    public Boolean getUseGemini() {
        return useGemini;
    }

    public void setUseGemini(Boolean useGemini) {
        this.useGemini = useGemini;
    }

    public static class HistoryDto {

        @JsonProperty("transaction_count")
        private int transactionCount;

        @JsonProperty("average_amount")
        private BigDecimal averageAmount;

        @JsonProperty("amount_stddev")
        private BigDecimal amountStddev;

        @JsonProperty("minimum_amount")
        private BigDecimal minimumAmount;

        @JsonProperty("maximum_amount")
        private BigDecimal maximumAmount;

        @JsonProperty("unique_counterparties")
        private int uniqueCounterparties;

        public HistoryDto() {
        }

        public HistoryDto(
                int transactionCount,
                BigDecimal averageAmount,
                BigDecimal amountStddev,
                BigDecimal minimumAmount,
                BigDecimal maximumAmount,
                int uniqueCounterparties
        ) {
            this.transactionCount = transactionCount;
            this.averageAmount = averageAmount;
            this.amountStddev = amountStddev;
            this.minimumAmount = minimumAmount;
            this.maximumAmount = maximumAmount;
            this.uniqueCounterparties = uniqueCounterparties;
        }

        public int getTransactionCount() {
            return transactionCount;
        }

        public void setTransactionCount(int transactionCount) {
            this.transactionCount = transactionCount;
        }

        public BigDecimal getAverageAmount() {
            return averageAmount;
        }

        public void setAverageAmount(BigDecimal averageAmount) {
            this.averageAmount = averageAmount;
        }

        public BigDecimal getAmountStddev() {
            return amountStddev;
        }

        public void setAmountStddev(BigDecimal amountStddev) {
            this.amountStddev = amountStddev;
        }

        public BigDecimal getMinimumAmount() {
            return minimumAmount;
        }

        public void setMinimumAmount(BigDecimal minimumAmount) {
            this.minimumAmount = minimumAmount;
        }

        public BigDecimal getMaximumAmount() {
            return maximumAmount;
        }

        public void setMaximumAmount(BigDecimal maximumAmount) {
            this.maximumAmount = maximumAmount;
        }

        public int getUniqueCounterparties() {
            return uniqueCounterparties;
        }

        public void setUniqueCounterparties(
                int uniqueCounterparties
        ) {
            this.uniqueCounterparties = uniqueCounterparties;
        }
    }
}