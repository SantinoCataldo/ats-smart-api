package com.atssmart.api.service;

import com.atssmart.api.dto.request.RecruiterProfileRequest;
import com.atssmart.api.dto.response.RecruiterProfileResponse;

import java.util.List;

public interface RecruiterProfileService {
    RecruiterProfileResponse create(RecruiterProfileRequest request);
    RecruiterProfileResponse getById(Long id);
    List<RecruiterProfileResponse> getAll();
    RecruiterProfileResponse update(Long id, RecruiterProfileRequest request, String email);
    void delete(Long id);
    RecruiterProfileResponse getProfile(String email);
    RecruiterProfileResponse updateProfile(RecruiterProfileRequest request, String email);
}
