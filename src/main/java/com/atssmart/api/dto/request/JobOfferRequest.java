package com.atssmart.api.dto.request;

import com.atssmart.api.enums.JobOfferModality;
import com.atssmart.api.enums.JobOfferStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for receiving JobOffer request payloads.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferRequest {

    @NotBlank(message = "El título de la oferta es obligatorio")
    private String title;

    @NotBlank(message = "La descripción de la oferta es obligatoria")
    private String description;

    @NotBlank(message = "El sector es obligatorio")
    private String sector;

    @NotBlank(message = "La ubicación es obligatoria")
    private String location;

    @NotNull(message = "La modalidad es obligatoria")
    private JobOfferModality modality;

    @NotNull(message = "El salario estimado es obligatorio")
    @Positive(message = "El salario debe ser un número positivo")
    private BigDecimal estimatedSalary;

    @NotNull(message = "El estado de la oferta es obligatorio")
    private JobOfferStatus status;

    private List<Long> skillIds;
}
