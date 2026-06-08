package com.atssmart.api.repository;

import com.atssmart.api.model.CandidateProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for the CandidateProfile entity.
 */
@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfileEntity, Long> {
    Optional<CandidateProfileEntity> findByUserEntityEmail(String email);
}
