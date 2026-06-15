    package com.atssmart.api.dto.response;

    import com.atssmart.api.enums.UserRole;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;

    /**
     * DTO for returning user details safely.
     * Skeletons left empty for group implementation.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserResponse {
        private Long id;
        private String email;
        private UserRole role;
        private LocalDateTime createdAt;
    }
