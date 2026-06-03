package com.atssmart.api.repository;

import com.atssmart.api.model.RecruiterProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the RecruiterProfile entity.
 */
@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfileEntity, Long> {
}
