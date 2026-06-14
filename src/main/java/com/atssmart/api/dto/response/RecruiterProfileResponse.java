package com.atssmart.api.dto.response;

import com.atssmart.api.model.CompanyEntity;
import com.atssmart.api.model.JobOfferEntity;
import com.atssmart.api.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecruiterProfileResponse {
    private Long id;
    private Long userId;
    private String fullName;
    private String companyRole;
    private Long companyId;
    private String companyName;
}
