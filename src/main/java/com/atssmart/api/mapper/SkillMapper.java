package com.atssmart.api.mapper;

import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.model.SkillEntity;
import org.springframework.stereotype.Component;

/**
 * Component to handle conversions between Skill Entity and DTOs.
 */
@Component
public class SkillMapper {

    /**
     * Converts a SkillRequest DTO into a Skill JPA entity.
     */
    public SkillEntity toEntity(SkillRequest request) {
        SkillEntity skill = new SkillEntity();
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        return skill;
    }

    /**
     * Converts a Skill JPA entity into a SkillResponse DTO.
     */
    public SkillResponse toResponse(SkillEntity skill) {
        return new SkillResponse(
                skill.getId(),
                skill.getName(),
                skill.getCategory()
        );
    }
}
