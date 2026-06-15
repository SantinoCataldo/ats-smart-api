package com.atssmart.api.dto.request;

import com.atssmart.api.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for receiving user request payloads.
 * Skeletons left empty for group implementation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Email(message = "email no validp")
    @NotBlank(message = "El email es obligatorio")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255)
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private UserRole role;
}
