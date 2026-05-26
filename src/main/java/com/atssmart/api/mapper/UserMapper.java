package com.atssmart.api.mapper;

import com.atssmart.api.dto.request.UserRequest;
import com.atssmart.api.dto.response.UserResponse;
import com.atssmart.api.model.Skill;
import com.atssmart.api.model.User;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/**
 * Component to handle conversions between User Entity and DTOs.
 */
@Component
public class UserMapper {

    /**
     * Converts a UserRequest DTO to a User Entity.
     */
    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // In production, this would be encrypted
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());
        user.setSeniority(request.getSeniority());
        user.setPortfolioLink(request.getPortfolioLink());
        return user;
    }

    /**
     * Converts a User Entity to a UserResponse DTO.
     */
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setRole(user.getRole());
        response.setSeniority(user.getSeniority());
        response.setPortfolioLink(user.getPortfolioLink());
        response.setCreatedAt(user.getCreatedAt());
        
        if (user.getSkills() != null) {
            response.setSkills(
                user.getSkills().stream()
                    .map(Skill::getName)
                    .collect(Collectors.toSet())
            );
        }
        return response;
    }
}
