package com.atssmart.api.controller;

import com.atssmart.api.service.AnalysisService;
import org.springframework.web.bind.annotation.*;

import com.atssmart.api.dto.request.JobApplicationRequest;
import com.atssmart.api.dto.response.JobApplicationResponse;
import com.atssmart.api.enums.ApplicationStatus;
import com.atssmart.api.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.List;

/**
 * REST Controller for JobApplication resource management.
 */
@RestController
@RequestMapping("/api/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private final AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<JobApplicationResponse> apply(
            @Valid @RequestBody JobApplicationRequest request,
            Principal principal) {
        String userEmail = principal.getName();
        JobApplicationResponse response = jobApplicationService.apply(request, userEmail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<JobApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        JobApplicationResponse response = jobApplicationService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<JobApplicationResponse>> getMyApplications(Principal principal) {
        String userEmail = principal.getName();
        List<JobApplicationResponse> history = jobApplicationService.getHistoryByCandidate(userEmail);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/by-offer/{offerId}")
    public ResponseEntity<List<JobApplicationResponse>> getApplicationsByOffer(@PathVariable Long offerId) {
        List<JobApplicationResponse> history = jobApplicationService.getHistoryByOffer(offerId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{jobOfferId}/ranking")
    public ResponseEntity<List<JobApplicationResponse>> getRanking(@PathVariable Long jobOfferId){
        return new ResponseEntity<>(jobApplicationService.getRankingMoreCompatibility(jobOfferId), HttpStatus.OK);
    }

    @PatchMapping("/{id}/analyze-difference")
    public ResponseEntity<JobApplicationResponse> analyzeDifference(@PathVariable Long id) {
        return new ResponseEntity<>(analysisService.analizeDifference(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/upload-cv")
    public ResponseEntity<JobApplicationResponse> uploadCv(
            @PathVariable Long id,
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file,
            Principal principal) {
        JobApplicationResponse response = jobApplicationService.uploadCv(id, file, principal.getName());
        return ResponseEntity.ok(response);
    }
}
