package com.atssmart.api.repository;

import com.atssmart.api.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the Company entity.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
