package com.atssmart.api.controller;

import com.atssmart.api.dto.request.CandidateProfileRequest;
import com.atssmart.api.dto.response.CandidateProfileResponse;
import com.atssmart.api.service.CandidateProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

/**
 * REST Controller for CandidateProfile management.
 */
@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<CandidateProfileResponse> getMyProfile(Principal principal) {
        return ResponseEntity.ok(candidateProfileService.getProfile(principal.getName()));
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<CandidateProfileResponse> updateMyProfile(
            @Valid @RequestBody CandidateProfileRequest request,
            Principal principal) {
        return ResponseEntity.ok(candidateProfileService.updateProfile(request, principal.getName()));
    }
}
