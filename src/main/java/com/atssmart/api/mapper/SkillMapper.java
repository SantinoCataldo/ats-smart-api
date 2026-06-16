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
        if (request == null) {
            return null;
        }
        SkillEntity skill = new SkillEntity();
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        return skill;
    }

    public SkillResponse toResponse(SkillEntity skill) {
        if (skill == null) {
            return null;
        }
        return new SkillResponse(
                skill.getId(),
                skill.getName(),
                skill.getCategory()
        );
    }
}
