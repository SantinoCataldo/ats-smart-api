package com.atssmart.api.service;

import com.atssmart.api.dto.request.CandidateProfileRequest;
import com.atssmart.api.dto.response.CandidateProfileResponse;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.CandidateProfileMapper;
import com.atssmart.api.model.CandidateProfileEntity;
import com.atssmart.api.model.SkillEntity;
import com.atssmart.api.repository.CandidateProfileRepository;
import com.atssmart.api.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service implementation for managing CandidateProfiles.
 */
@Service
@RequiredArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final SkillRepository skillRepository;
    private final CandidateProfileMapper candidateProfileMapper;

    @Override
    @Transactional(readOnly = true)
    public CandidateProfileResponse getProfile(String email) {
        CandidateProfileEntity profile = candidateProfileRepository.findByUserEntityEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de candidato no encontrado para el usuario: " + email));
        return candidateProfileMapper.toResponse(profile);
    }

    @Override
    @Transactional
    public CandidateProfileResponse updateProfile(CandidateProfileRequest request, String email) {
        CandidateProfileEntity profile = candidateProfileRepository.findByUserEntityEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de candidato no encontrado para el usuario: " + email));

        profile.setFullName(request.fullName());
        profile.setSeniority(request.seniority());
        profile.setCvLink(request.cvLink());

        if (request.skillIds() != null && !request.skillIds().isEmpty()) {
            List<SkillEntity> skills = skillRepository.findAllById(request.skillIds());
            profile.setSkills(new HashSet<>(skills));
        } else {
            profile.getSkills().clear();
        }

        CandidateProfileEntity saved = candidateProfileRepository.save(profile);
        return candidateProfileMapper.toResponse(saved);
    }
}
