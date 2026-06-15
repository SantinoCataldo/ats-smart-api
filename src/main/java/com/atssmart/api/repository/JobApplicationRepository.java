package com.atssmart.api.repository;

import com.atssmart.api.model.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for the JobApplication entity.
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Long> {
    boolean existsByApplicantIdAndJobOfferId(Long applicantId, Long jobOfferId);
    List<JobApplicationEntity> findByApplicantUserEntityEmail(String email);
    List<JobApplicationEntity> findByJobOfferId(Long jobOfferId);
    List<JobApplicationEntity> findByJobOfferIdOrderByMatchScoreDesc(Long jobOfferId);
}
