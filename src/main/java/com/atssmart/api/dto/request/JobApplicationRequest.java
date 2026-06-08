package com.atssmart.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for receiving JobApplication request payloads.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequest {
    @NotNull(message = "El ID de la oferta de trabajo es obligatorio")
    private Long jobOfferId;
}
