package com.atssmart.api.controller;

import com.atssmart.api.dto.request.CompanyRequest;
import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.CompanyResponse;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.service.CompanyService;
import com.atssmart.api.service.CompanyServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Company resource management.
 * Skeleton left empty for group implementation.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request){
        return new ResponseEntity<>(companyService.create(request),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request){
        return new ResponseEntity<>(companyService.update(id,request),HttpStatus.OK);
    }

    @GetMapping("{/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Long id){
        return new ResponseEntity<>(companyService.getById(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll(){
        return new ResponseEntity<>(companyService.getAll(),HttpStatus.OK);
    }
}

