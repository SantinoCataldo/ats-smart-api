package com.atssmart.api.model;

import com.atssmart.api.enums.SkillCategory;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a Technical Skill required for job offers or possessed by users.
 */
@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SkillCategory category;
}
