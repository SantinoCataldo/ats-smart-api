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

    public SkillEntity toEntity(SkillRequest request) {
        SkillEntity skill = new SkillEntity();
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        return skill;
    }

    public SkillResponse toResponse(SkillEntity skill) {
        return new SkillResponse(
                skill.getId(),
                skill.getName(),
                skill.getCategory()
        );
    }
}
