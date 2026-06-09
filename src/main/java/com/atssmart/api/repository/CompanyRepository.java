package com.atssmart.api.repository;

import com.atssmart.api.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the Company entity.
 */
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    boolean existsByNameIgnoreCase(String name);
}
