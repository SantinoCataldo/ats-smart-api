package com.atssmart.api.dto.request;

import com.atssmart.api.enums.SkillCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for receiving Skill request payloads (create and update).
 */
@Data
public class SkillRequest {

    @NotBlank(message = "El nombre de la habilidad es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
    private String name;

    @NotNull(message = "La categoría de la habilidad es obligatoria")
    private SkillCategory category;
}
