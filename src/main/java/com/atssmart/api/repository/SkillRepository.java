package com.atssmart.api.repository;

import com.atssmart.api.enums.SkillCategory;
import com.atssmart.api.model.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for the Skill entity.
 */
@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {

    boolean existsByNameIgnoreCase(String name);

    List<SkillEntity> findByCategory(SkillCategory category);
}
