package com.atssmart.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a Recruiter profile.
 */
@Entity
@Table(name = "recruiter_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity userEntity;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "company_role", length = 100)
    private String companyRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Company company;

    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<JobOffer> offers = new ArrayList<>();
}
