package com.atssmart.api.model;

import com.atssmart.api.enums.JobOfferStatus;
import com.atssmart.api.enums.JobOfferModality;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a Job Offer published in the job board.
 * A Job Offer is published and managed by a Recruiter (implicitly linking it to a Company).
 */
@Entity
@Table(name = "job_offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RecruiterProfile recruiter;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "sector", length = 100)
    private String sector;

    @Column(name = "location", length = 150)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "modality", length = 30)
    private JobOfferModality modality;

    @Column(name = "estimated_salary", precision = 12, scale = 2)
    private BigDecimal estimatedSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private JobOfferStatus status;

    @Column(name = "published_at", nullable = false, updatable = false)
    private LocalDateTime publishedAt;

    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<JobApplication> applications = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "job_offer_skill",
        joinColumns = @JoinColumn(name = "job_offer_id"),
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
