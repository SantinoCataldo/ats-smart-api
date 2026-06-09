package com.atssmart.api.service;

import com.atssmart.api.dto.request.CandidateProfileRequest;
import com.atssmart.api.dto.response.CandidateProfileResponse;

/**
 * Service interface for managing CandidateProfiles.
 */
public interface CandidateProfileService {
    CandidateProfileResponse getProfile(String email);
    CandidateProfileResponse updateProfile(CandidateProfileRequest request, String email);
}
