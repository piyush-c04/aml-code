package com.aml.common.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    @NotBlank(message = "Account ID is required")
    private String accountId;

    @NotBlank(message = "Account holder type is required")
    private String accountHolderType;

    @NotBlank(message = "KYC verification status is required")
    private String kycVerificationStatus;

    private Boolean riskCountryFlag;
}
