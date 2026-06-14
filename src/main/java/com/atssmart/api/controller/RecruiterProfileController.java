package com.atssmart.api.controller;

import com.atssmart.api.dto.request.RecruiterProfileRequest;
import com.atssmart.api.dto.response.RecruiterProfileResponse;
import com.atssmart.api.service.RecruiterProfileServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recruiter-profiles")
public class RecruiterProfileController {
    private final RecruiterProfileServiceImpl recruiterProfileService;

    @PostMapping
    public ResponseEntity<RecruiterProfileResponse> create(@Valid @RequestBody RecruiterProfileRequest request){
        return new ResponseEntity<>(recruiterProfileService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruiterProfileResponse> update(@PathVariable Long id,@Valid @RequestBody RecruiterProfileRequest request){
        return new ResponseEntity<>(recruiterProfileService.update(id,request),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        recruiterProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RecruiterProfileResponse>> getAll(){
        return new ResponseEntity<>(recruiterProfileService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruiterProfileResponse> getById(@PathVariable Long id){
        return new ResponseEntity<>(recruiterProfileService.getById(id),HttpStatus.OK);
    }

}
