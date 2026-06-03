package com.atssmart.api.repository;

import com.atssmart.api.model.CandidateProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the CandidateProfile entity.
 */
@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfileEntity, Long> {
}
