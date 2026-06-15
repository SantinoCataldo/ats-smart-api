package com.atssmart.api.mapper;

import org.springframework.stereotype.Component;

import com.atssmart.api.dto.request.JobOfferRequest;
import com.atssmart.api.dto.response.JobOfferResponse;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.model.JobOfferEntity;
import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Component to handle conversions between JobOffer Entity and DTOs.
 */
@Component
@RequiredArgsConstructor
public class JobOfferMapper {

    private final SkillMapper skillMapper;

    public JobOfferEntity toEntity(JobOfferRequest request) {
        if (request == null) {
            return null;
        }
        JobOfferEntity entity = new JobOfferEntity();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setSector(request.getSector());
        entity.setLocation(request.getLocation());
        entity.setModality(request.getModality());
        entity.setEstimatedSalary(request.getEstimatedSalary());
        entity.setStatus(request.getStatus());
        return entity;
    }

    public JobOfferResponse toResponse(JobOfferEntity entity) {
        if (entity == null) {
            return null;
        }

        Set<SkillResponse> skills = new HashSet<>();
        if (entity.getRequiredSkills() != null) {
            skills = entity.getRequiredSkills().stream()
                    .map(skillMapper::toResponse)
                    .collect(Collectors.toSet());
        }

        String recruiterName = null;
        String companyName = null;
        Long recruiterId = null;

        if (entity.getRecruiter() != null) {
            recruiterId = entity.getRecruiter().getId();
            recruiterName = entity.getRecruiter().getFullName();
            if (entity.getRecruiter().getCompany() != null) {
                companyName = entity.getRecruiter().getCompany().getName();
            }
        }

        return new JobOfferResponse(
                entity.getId(),
                recruiterId,
                recruiterName,
                companyName,
                entity.getTitle(),
                entity.getDescription(),
                entity.getSector(),
                entity.getLocation(),
                entity.getModality() != null ? entity.getModality().name() : null,
                entity.getEstimatedSalary(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getPublishedAt(),
                skills
        );
    }
}
