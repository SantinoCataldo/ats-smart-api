package com.atssmart.api.repository;

import com.atssmart.api.enums.SkillCategory;
import com.atssmart.api.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for the Skill entity.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    boolean existsByNameIgnoreCase(String name);

    List<Skill> findByCategory(SkillCategory category);
}
