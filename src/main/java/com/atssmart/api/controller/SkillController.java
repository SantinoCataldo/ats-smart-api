package com.atssmart.api.controller;

import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.enums.SkillCategory;
import com.atssmart.api.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Skill resource management.
 */
@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<SkillResponse> create(@Valid @RequestBody SkillRequest request){
        return new ResponseEntity<>(skillService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAll(@RequestParam(required = false) SkillCategory category){
        List<SkillResponse> skills;
        if (category != null) {
            skills = skillService.getByCategory(category);
        } else {
            skills = skillService.getAll();
        }
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(skillService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> update(@PathVariable Long id, @Valid @RequestBody SkillRequest request){
        return ResponseEntity.ok(skillService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
