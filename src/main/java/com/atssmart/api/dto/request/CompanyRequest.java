package com.atssmart.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CompanyRequest {
    @NotBlank(message = "El nombre de la compania es obligatorio")
    private String name;

    @Size(max = 500)
    private String description;

    @URL
    @Size(max = 255)
    private String website;

    @URL
    @Size(max = 255)
    private String logoUrl;


}
