package com.atssmart.api.controller;

import org.springframework.web.bind.annotation.*;

import com.atssmart.api.dto.request.JobOfferRequest;
import com.atssmart.api.dto.response.JobOfferResponse;
import com.atssmart.api.enums.JobOfferModality;
import com.atssmart.api.service.JobOfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.List;

/**
 * REST Controller for JobOffer resource management.
 */
@RestController
@RequestMapping("/api/job-offers")
@RequiredArgsConstructor
public class JobOfferController {
    private final JobOfferService jobOfferService;

    @PostMapping
    public ResponseEntity<JobOfferResponse> create(@Valid @RequestBody JobOfferRequest request, Principal principal) {
        JobOfferResponse response = jobOfferService.create(request, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOfferResponse> update(@PathVariable Long id, @Valid @RequestBody JobOfferRequest request, Principal principal) {
        JobOfferResponse response = jobOfferService.update(id, request, principal.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponse> getById(@PathVariable Long id) {
        JobOfferResponse response = jobOfferService.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        jobOfferService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobOfferResponse>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) JobOfferModality modality,
            @RequestParam(required = false) List<Long> skillIds) {
        List<JobOfferResponse> response = jobOfferService.search(title, sector, location, modality, skillIds);
        return ResponseEntity.ok(response);
    }
}
