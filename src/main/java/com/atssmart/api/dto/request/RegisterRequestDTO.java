package com.atssmart.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "El email es obligatorio")
        String email,

        @NotBlank(message = "La password es obligatoria")
        @Size(min = 4, message = "La password debe tener al menos 4 caracteres")
        String password
) {
}
