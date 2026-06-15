package com.atssmart.api.service;

import com.atssmart.api.dto.request.JobOfferRequest;
import com.atssmart.api.dto.response.JobOfferResponse;
import com.atssmart.api.enums.JobOfferModality;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.JobOfferMapper;
import com.atssmart.api.model.JobOfferEntity;
import com.atssmart.api.model.RecruiterProfileEntity;
import com.atssmart.api.model.SkillEntity;
import com.atssmart.api.repository.JobOfferRepository;
import com.atssmart.api.repository.RecruiterProfileRepository;
import com.atssmart.api.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;

/**
 * Service implementation for managing Job Offers.
 */
@Service
@RequiredArgsConstructor
public class JobOfferServiceImpl implements JobOfferService {
    private final JobOfferRepository jobOfferRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final SkillRepository skillRepository;
    private final JobOfferMapper jobOfferMapper;

    @Override
    @Transactional
    public JobOfferResponse create(JobOfferRequest request, String recruiterEmail) {
        RecruiterProfileEntity recruiter = recruiterProfileRepository.findByUserEntityEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de reclutador no encontrado para el usuario: " + recruiterEmail));

        JobOfferEntity entity = jobOfferMapper.toEntity(request);
        entity.setRecruiter(recruiter);

        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            List<SkillEntity> skills = skillRepository.findAllById(request.getSkillIds());
            entity.setRequiredSkills(new HashSet<>(skills));
        }

        JobOfferEntity saved = jobOfferRepository.save(entity);
        return jobOfferMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public JobOfferResponse update(Long id, JobOfferRequest request, String recruiterEmail) {
        JobOfferEntity entity = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta laboral", "id", id));

        if (!entity.getRecruiter().getUserEntity().getEmail().equalsIgnoreCase(recruiterEmail)) {
            throw new IllegalArgumentException("No tienes permiso para modificar esta oferta laboral");
        }

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setSector(request.getSector());
        entity.setLocation(request.getLocation());
        entity.setModality(request.getModality());
        entity.setEstimatedSalary(request.getEstimatedSalary());
        entity.setStatus(request.getStatus());

        if (request.getSkillIds() != null) {
            List<SkillEntity> skills = skillRepository.findAllById(request.getSkillIds());
            entity.setRequiredSkills(new HashSet<>(skills));
        } else {
            entity.getRequiredSkills().clear();
        }

        JobOfferEntity saved = jobOfferRepository.save(entity);
        return jobOfferMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public JobOfferResponse getById(Long id) {
        JobOfferEntity entity = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta laboral", "id", id));
        return jobOfferMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void delete(Long id, String recruiterEmail) {
        JobOfferEntity entity = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta laboral", "id", id));

        if (!entity.getRecruiter().getUserEntity().getEmail().equalsIgnoreCase(recruiterEmail)) {
            throw new IllegalArgumentException("No tienes permiso para eliminar esta oferta laboral");
        }

        jobOfferRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobOfferResponse> search(String title, String sector, String location, JobOfferModality modality, List<Long> skillIds) {
        List<JobOfferEntity> offers = jobOfferRepository.searchOffers(title, sector, location, modality, skillIds);
        return offers.stream()
                .map(jobOfferMapper::toResponse)
                .toList();
    }
}
