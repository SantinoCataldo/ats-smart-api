package com.atssmart.api.mapper;

import com.atssmart.api.dto.response.RecruiterProfileResponse;
import com.atssmart.api.model.RecruiterProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class RecruiterProfileMapper {

    public RecruiterProfileResponse toResponse(RecruiterProfileEntity entity) {
        if (entity == null) {
            return null;
        }
        RecruiterProfileResponse response = new RecruiterProfileResponse();
        response.setId(entity.getId());
        response.setFullName(entity.getFullName());
        response.setCompanyRole(entity.getCompanyRole());
        if (entity.getUserEntity() != null) {
            response.setUserId(entity.getUserEntity().getId());
        }
        if (entity.getCompany() != null) {
            response.setCompanyId(entity.getCompany().getId());
            response.setCompanyName(entity.getCompany().getName());
        }
        return response;
    }
}
