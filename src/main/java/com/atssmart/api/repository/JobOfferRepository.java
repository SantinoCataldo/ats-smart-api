package com.atssmart.api.repository;

import com.atssmart.api.enums.JobOfferStatus;
import com.atssmart.api.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA Repository for the JobOffer entity.
 */
@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByStatus(JobOfferStatus status);
}
