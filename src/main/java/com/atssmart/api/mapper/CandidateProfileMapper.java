package com.atssmart.api.mapper;

import com.atssmart.api.dto.response.CandidateProfileResponse;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.model.CandidateProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Component to handle conversions between CandidateProfile Entity and DTOs.
 */
@Component
@RequiredArgsConstructor
public class CandidateProfileMapper {

    private final SkillMapper skillMapper;

    public CandidateProfileResponse toResponse(CandidateProfileEntity entity) {
        if (entity == null) {
            return null;
        }

        Set<SkillResponse> skillResponses = entity.getSkills() == null ? Set.of() :
                entity.getSkills().stream()
                        .map(skillMapper::toResponse)
                        .collect(Collectors.toSet());

        return new CandidateProfileResponse(
                entity.getId(),
                entity.getUserEntity() != null ? entity.getUserEntity().getEmail() : null,
                entity.getFullName(),
                entity.getSeniority(),
                entity.getCvLink(),
                skillResponses
        );
    }
}
