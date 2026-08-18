package com.aml.common.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String id;
    private String accountId;
    private String accountHash;
    private String accountHolderType;
    private String kycVerificationStatus;
    private Boolean riskCountryFlag;
    private LocalDateTime createdAt;
}
