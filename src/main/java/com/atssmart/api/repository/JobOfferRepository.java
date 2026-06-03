package com.atssmart.api.repository;

import com.atssmart.api.model.JobOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the JobOffer entity.
 */
@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Long> {
}
