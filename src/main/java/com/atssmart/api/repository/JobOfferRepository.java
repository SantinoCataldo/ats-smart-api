package com.atssmart.api.repository;

import com.atssmart.api.model.JobOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atssmart.api.enums.JobOfferModality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA Repository for the JobOffer entity.
 */
@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Long> {

    @Query("SELECT DISTINCT j FROM JobOfferEntity j LEFT JOIN j.requiredSkills s " +
           "WHERE j.status = 'ACTIVE' " +
           "AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
           "AND (:sector IS NULL OR LOWER(j.sector) = LOWER(:sector)) " +
           "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (:modality IS NULL OR j.modality = :modality) " +
           "AND (COALESCE(:skillIds, NULL) IS NULL OR s.id IN :skillIds)")
    List<JobOfferEntity> searchOffers(
        @Param("title") String title,
        @Param("sector") String sector,
        @Param("location") String location,
        @Param("modality") JobOfferModality modality,
        @Param("skillIds") List<Long> skillIds
    );
}
