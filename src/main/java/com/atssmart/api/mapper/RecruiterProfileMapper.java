package com.atssmart.api.mapper;

import com.atssmart.api.dto.response.RecruiterProfileResponse;
import com.atssmart.api.model.RecruiterProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class RecruiterProfileMapper {

    public RecruiterProfileResponse toResponse(RecruiterProfileEntity entity) {
        RecruiterProfileResponse response = new RecruiterProfileResponse();
        response.setId(entity.getId());
        response.setFullName(entity.getFullName());
        response.setCompanyRole(entity.getCompanyRole());
        response.setUserId(entity.getUserEntity().getId());
        response.setCompanyId(entity.getCompany().getId());
        response.setCompanyName(entity.getCompany().getName());
        return response;
    }
}
