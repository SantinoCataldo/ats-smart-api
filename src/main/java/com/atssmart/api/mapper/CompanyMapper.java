package com.atssmart.api.mapper;

import com.atssmart.api.dto.request.CompanyRequest;
import com.atssmart.api.dto.response.CompanyResponse;
import com.atssmart.api.model.CompanyEntity;
import org.springframework.stereotype.Component;

/**
 * Component to handle conversions between Company Entity and DTOs.
 * Skeleton left empty for group implementation.
 */

@Component
public class CompanyMapper {

    public CompanyEntity toEntity(CompanyRequest request) {
        CompanyEntity company = new CompanyEntity();
        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLogoUrl(request.getLogoUrl());
        return company;
    }

    public CompanyResponse toResponse(CompanyEntity company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getDescription(),
                company.getWebsite(),
                company.getLogoUrl(),
                company.getCreatedAt()
        );
    }
}
