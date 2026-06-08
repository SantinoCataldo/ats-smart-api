package com.atssmart.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO representing a JobApplication response payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponse {
    private Long id;
    private Long candidateProfileId;
    private String candidateName;
    private Long jobOfferId;
    private String jobOfferTitle;
    private Integer matchScore;
    private String aiFeedback;
    private String status;
    private LocalDateTime appliedAt;
}
