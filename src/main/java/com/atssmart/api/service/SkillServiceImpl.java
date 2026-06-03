package com.atssmart.api.service;

import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.enums.SkillCategory;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.SkillMapper;
import com.atssmart.api.model.Skill;
import com.atssmart.api.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of SkillService with full CRUD logic and name-uniqueness validation (RF23).
 */
@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    @Transactional
    public SkillResponse create(SkillRequest request) {
        if (skillRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Ya existe una habilidad con el nombre: " + request.getName());
        }
        Skill skill = skillMapper.toEntity(request);
        Skill saved = skillRepository.save(skill);
        return skillMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SkillResponse getById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));
        return skillMapper.toResponse(skill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getAll() {
        return skillRepository.findAll().stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getByCategory(SkillCategory category) {
        return skillRepository.findByCategory(category).stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public SkillResponse update(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));

        if (!skill.getName().equalsIgnoreCase(request.getName())
                && skillRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Ya existe una habilidad con el nombre: " + request.getName());
        }

        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        Skill updated = skillRepository.save(skill);
        return skillMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill", "id", id);
        }
        skillRepository.deleteById(id);
    }
}
