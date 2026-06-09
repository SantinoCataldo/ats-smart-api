package com.atssmart.api.dto.request;

import com.atssmart.api.enums.Seniority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

/**
 * DTO for receiving CandidateProfile update payloads.
 * Seniority is optional (nullable) for roles where it does not apply.
 */
public record CandidateProfileRequest(
        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 150, message = "El nombre completo no puede superar los 150 caracteres")
        String fullName,

        Seniority seniority,

        @Size(max = 255, message = "El enlace al CV no puede superar los 255 caracteres")
        String cvLink,

        Set<Long> skillIds
) {
}
