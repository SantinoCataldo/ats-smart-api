package com.atssmart.api.dto.response;

import com.atssmart.api.enums.SkillCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for returning Skill details safely (never exposing the JPA entity directly).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponse {

    private Long id;
    private String name;
    private SkillCategory category;
}
