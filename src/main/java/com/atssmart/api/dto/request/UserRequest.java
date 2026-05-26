package com.atssmart.api.dto.request;

import com.atssmart.api.enums.Seniority;
import com.atssmart.api.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * DTO for receiving user creation or update request payloads.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String fullName;

    @NotNull(message = "El rol es obligatorio")
    private UserRole role;

    private Seniority seniority;

    private String portfolioLink;

    private Set<Long> skillIds;
}
