package com.atssmart.api.repository;

import com.atssmart.api.model.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the JobApplication entity.
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Long> {
}
