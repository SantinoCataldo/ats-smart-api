package com.atssmart.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecruiterProfileRequest {
    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150)
    private String fullName;

    @Size(max = 100)
    private String companyRole;

    @NotNull(message = "La empresa es obligatoria")
    private Long companyId;
}
