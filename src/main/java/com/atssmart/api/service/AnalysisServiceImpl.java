package com.atssmart.api.service;

import com.atssmart.api.dto.response.JobApplicationResponse;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.JobApplicationMapper;
import com.atssmart.api.model.JobApplicationEntity;
import com.atssmart.api.model.SkillEntity;
import com.atssmart.api.repository.JobApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;

    @Transactional
    public JobApplicationResponse analizeDifference(Long JobApplicationId){
        JobApplicationEntity jobApplication = jobApplicationRepository.findById(JobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Application","id",JobApplicationId));

        Set<SkillEntity> JobOfferSkills = jobApplication.getJobOffer().getRequiredSkills();
        Set<SkillEntity> ApplicantSkills = jobApplication.getApplicant().getSkills();

        Set<SkillEntity> missing = new HashSet<>(JobOfferSkills);
        missing.removeAll(ApplicantSkills);
        jobApplication.setMissingSkills(missing);
        return jobApplicationMapper.toResponse(jobApplicationRepository.save(jobApplication));
    }
}
