package com.atssmart.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for returning JobOffer details safely.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferResponse {
    private Long id;
    private Long recruiterId;
    private String recruiterName;
    private String companyName;
    private String title;
    private String description;
    private String sector;
    private String location;
    private String modality;
    private BigDecimal estimatedSalary;
    private String status;
    private LocalDateTime publishedAt;
    private Set<SkillResponse> requiredSkills;
}
