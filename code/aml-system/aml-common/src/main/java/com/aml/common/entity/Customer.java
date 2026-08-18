package com.aml.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "account_holder_type", length = 50)
    private String accountHolderType;

    @Column(name = "kyc_verification_status", length = 50)
    private String kycVerificationStatus;

    @Column(name = "risk_country_flag")
    private Boolean riskCountryFlag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (riskCountryFlag == null) riskCountryFlag = false;
    }
}
