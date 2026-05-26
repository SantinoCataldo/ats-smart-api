package com.atssmart.api.service;

import com.atssmart.api.dto.request.UserRequest;
import com.atssmart.api.dto.response.UserResponse;
import java.util.List;

/**
 * Service interface for managing Users.
 */
public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);
}
