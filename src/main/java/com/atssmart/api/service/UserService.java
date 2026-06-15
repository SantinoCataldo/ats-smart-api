package com.atssmart.api.service;

import com.atssmart.api.dto.request.UserRequest;
import com.atssmart.api.dto.response.UserResponse;

import java.util.List;

/**
 * Service interface for managing Users.
 * Skeletons left empty for group implementation.
 */
public interface UserService {
    UserResponse create(UserRequest request);
    UserResponse getById(Long id);
    List<UserResponse> getAll();
    UserResponse update(Long id, UserRequest request);
    void delete(Long id);
}
