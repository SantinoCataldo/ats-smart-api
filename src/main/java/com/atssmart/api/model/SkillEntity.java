package com.atssmart.api.model;

import com.atssmart.api.enums.SkillCategory;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a generic Skill required for job offers or possessed by candidates.
 */
@Entity
@Table(name = "skills")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SkillCategory category;
}
