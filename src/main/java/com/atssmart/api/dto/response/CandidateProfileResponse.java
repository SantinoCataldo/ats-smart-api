package com.atssmart.api.dto.response;

import com.atssmart.api.enums.Seniority;
import java.util.Set;

/**
 * DTO representing a CandidateProfile response payload.
 */
public record CandidateProfileResponse(
        Long id,
        String email,
        String fullName,
        Seniority seniority,
        String cvLink,
        Set<SkillResponse> skills
) {
}
