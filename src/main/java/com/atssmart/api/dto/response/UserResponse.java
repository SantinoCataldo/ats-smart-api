package com.atssmart.api.dto.response;

import com.atssmart.api.enums.Seniority;
import com.atssmart.api.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for returning user details safely.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private Seniority seniority;
    private String portfolioLink;
    private LocalDateTime createdAt;
    private Set<String> skills;
}
