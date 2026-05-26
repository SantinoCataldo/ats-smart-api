package com.atssmart.api.model;

import com.atssmart.api.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Candidate's Application (Postulación) to a Job Offer.
 */
@Entity
@Table(name = "postulacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private JobOffer jobOffer;

    @Column(name = "match_score")
    private Integer matchScore;

    @Column(name = "feedback_ia", columnDefinition = "TEXT")
    private String aiFeedback;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_proceso", nullable = false, length = 30)
    private ApplicationStatus processStatus;

    @Column(name = "fecha_postulacion", nullable = false)
    private LocalDateTime appliedAt;

    // AI identifies missing skills for this application (postulacion_skill_faltante)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "postulacion_skill_faltante",
        joinColumns = @JoinColumn(name = "postulacion_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Skill> missingSkills = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (this.appliedAt == null) {
            this.appliedAt = LocalDateTime.now();
        }
    }
}
