package com.atssmart.api.mapper;

import org.springframework.stereotype.Component;

import com.atssmart.api.dto.response.JobApplicationResponse;
import com.atssmart.api.model.JobApplicationEntity;

/**
 * Component to handle conversions between JobApplication Entity and DTOs.
 */
@Component
public class JobApplicationMapper {

    public JobApplicationResponse toResponse(JobApplicationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new JobApplicationResponse(
                entity.getId(),
                entity.getApplicant() != null ? entity.getApplicant().getId() : null,
                entity.getApplicant() != null ? entity.getApplicant().getFullName() : null,
                entity.getJobOffer() != null ? entity.getJobOffer().getId() : null,
                entity.getJobOffer() != null ? entity.getJobOffer().getTitle() : null,
                entity.getMatchScore(),
                entity.getAiFeedback(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getAppliedAt(),
                entity.getCvLink()
        );
    }
}
