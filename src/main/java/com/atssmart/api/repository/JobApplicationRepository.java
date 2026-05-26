package com.atssmart.api.repository;

import com.atssmart.api.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA Repository for the JobApplication entity.
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByApplicantId(Long applicantId);
    List<JobApplication> findByJobOfferId(Long jobOfferId);
}
