package com.atssmart.api.service;

import com.atssmart.api.dto.request.JobApplicationRequest;
import com.atssmart.api.dto.response.JobApplicationResponse;
import com.atssmart.api.enums.ApplicationStatus;
import com.atssmart.api.enums.JobOfferStatus;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.JobApplicationMapper;
import com.atssmart.api.model.CandidateProfileEntity;
import com.atssmart.api.model.JobApplicationEntity;
import com.atssmart.api.model.JobOfferEntity;
import com.atssmart.api.repository.CandidateProfileRepository;
import com.atssmart.api.repository.JobApplicationRepository;
import com.atssmart.api.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service implementation for managing Job Applications.
 */
@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobOfferRepository jobOfferRepository;
    private final JobApplicationMapper jobApplicationMapper;

    @Override
    @Transactional
    public JobApplicationResponse apply(JobApplicationRequest request, String userEmail) {
        CandidateProfileEntity candidate = candidateProfileRepository.findByUserEntityEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de candidato no encontrado para el usuario: " + userEmail));

        JobOfferEntity offer = jobOfferRepository.findById(request.getJobOfferId())
                .orElseThrow(() -> new ResourceNotFoundException("Oferta laboral", "id", request.getJobOfferId()));

        if (offer.getStatus() != JobOfferStatus.ACTIVE) {
            throw new IllegalArgumentException("No se puede postular a una oferta laboral que no esté ACTIVA");
        }

        if (jobApplicationRepository.existsByApplicantIdAndJobOfferId(candidate.getId(), offer.getId())) {
            throw new IllegalArgumentException("Ya te has postulado a esta oferta laboral");
        }

        JobApplicationEntity application = new JobApplicationEntity();
        application.setApplicant(candidate);
        application.setJobOffer(offer);
        application.setStatus(ApplicationStatus.PENDING);
        
        JobApplicationEntity saved = jobApplicationRepository.save(application);
        return jobApplicationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public JobApplicationResponse updateStatus(Long id, ApplicationStatus status) {
        JobApplicationEntity application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulación", "id", id));
        
        application.setStatus(status);
        JobApplicationEntity updated = jobApplicationRepository.save(application);
        return jobApplicationMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponse> getHistoryByCandidate(String userEmail) {
        return jobApplicationRepository.findByApplicantUserEntityEmail(userEmail).stream()
                .map(jobApplicationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponse> getHistoryByOffer(Long jobOfferId) {
        if (!jobOfferRepository.existsById(jobOfferId)) {
            throw new ResourceNotFoundException("Oferta laboral", "id", jobOfferId);
        }
        return jobApplicationRepository.findByJobOfferId(jobOfferId).stream()
                .map(jobApplicationMapper::toResponse)
                .toList();
    }
}
