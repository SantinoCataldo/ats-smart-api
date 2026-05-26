package com.atssmart.api.model;

import com.atssmart.api.enums.JobOfferStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a Job Offer published in the ATS system.
 */
@Entity
@Table(name = "oferta_laboral")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String title;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "salario_estimado", precision = 12, scale = 2)
    private BigDecimal estimatedSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private JobOfferStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reclutador_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User recruiter;

    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime publishedAt;

    // A job offer receives applications
    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<JobApplication> applications = new ArrayList<>();

    // A job offer requires skills (oferta_laboral_skill)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "oferta_laboral_skill",
        joinColumns = @JoinColumn(name = "oferta_laboral_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Skill> requiredSkills = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.publishedAt = LocalDateTime.now();
    }
}
