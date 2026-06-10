package com.atssmart.api.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for returning Company details safely.
 * Skeleton left empty for group implementation.
 */

@Data
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private String name;
    private String description;
    private String website;
    private String logoUrl;
    private LocalDateTime createdAt;
}
