package com.atssmart.api.controller;

import com.atssmart.api.dto.request.CandidateProfileRequest;
import com.atssmart.api.dto.response.CandidateProfileResponse;
import com.atssmart.api.service.CandidateProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
@Tag(name = "Perfil del Candidato", description = "Endpoints para que el postulante gestione su información curricular y habilidades laborales")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    @Operation(summary = "Obtener mi perfil", description = "Devuelve de forma completa el perfil del candidato autenticado basándose en su token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil recuperado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Token de acceso faltante o expirado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, requiere rol CANDIDATE")
    })
    @GetMapping("/me")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<CandidateProfileResponse> getMyProfile(Principal principal) {
        return ResponseEntity.ok(candidateProfileService.getProfile(principal.getName()));
    }

    @Operation(summary = "Actualizar mi perfil", description = "Permite al candidato actualizar su nombre completo, nivel de seniority, enlace externo del currículum y catálogo de habilidades poseídas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil modificado y sincronizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Estructura de payload de actualización incorrecta")
    })
    @PutMapping("/me")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<CandidateProfileResponse> updateMyProfile(
            @Valid @RequestBody CandidateProfileRequest request,
            Principal principal) {
        return ResponseEntity.ok(candidateProfileService.updateProfile(request, principal.getName()));
    }
}