package com.atssmart.api.mapper;

import com.atssmart.api.dto.response.UserResponse;
import com.atssmart.api.model.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Component to handle conversions between User Entity and DTOs.
 * Skeletons left empty for group implementation.
 */
@Component
public class UserMapper {

    public UserResponse toResponse(UserEntity entity) {
        UserResponse Userresponse = new UserResponse();
        Userresponse.setId(entity.getId());
        Userresponse.setEmail(entity.getEmail());
        Userresponse.setRole(entity.getRole());
        Userresponse.setCreatedAt(entity.getCreatedAt());
        return Userresponse;
    }

}