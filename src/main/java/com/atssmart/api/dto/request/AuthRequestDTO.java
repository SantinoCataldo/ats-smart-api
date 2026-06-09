package com.atssmart.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    @NotNull(message = "El email debe ser obligatorio")
    private String email;

    @NotNull(message = "El email debe ser obligatorio")
    private String password;


}
