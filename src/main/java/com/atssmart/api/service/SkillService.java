package com.atssmart.api.service;

import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.enums.SkillCategory;

import java.util.List;

/**
 * Service interface for Skill business logic.
 */
public interface SkillService {

    SkillResponse create(SkillRequest request);

    SkillResponse getById(Long id);

    List<SkillResponse> getAll();

    List<SkillResponse> getByCategory(SkillCategory category);

    SkillResponse update(Long id, SkillRequest request);

    void delete(Long id);
}
