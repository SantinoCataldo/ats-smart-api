package com.atssmart.api.controller;

import com.atssmart.api.dto.request.JobApplicationRequest;
import com.atssmart.api.dto.response.JobApplicationResponse;
import com.atssmart.api.enums.ApplicationStatus;
import com.atssmart.api.service.AnalysisService;
import com.atssmart.api.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
@RequiredArgsConstructor
@Tag(name = "Postulaciones", description = "Endpoints para aplicar a ofertas, subir currículums y disparar el análisis con Inteligencia Artificial")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private final AnalysisService analysisService;

    @Operation(summary = "Aplicar a oferta", description = "Genera una nueva postulación para el candidato autenticado hacia una oferta específica.")
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping
    public ResponseEntity<JobApplicationResponse> apply(
            @Valid @RequestBody JobApplicationRequest request,
            Principal principal) {
        JobApplicationResponse response = jobApplicationService.apply(request, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar estado de postulación", description = "Cambia el estado de una postulación (Ej. de PENDIENTE a EN_REVISION).")
    @PreAuthorize("hasRole('RECRUITER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<JobApplicationResponse> updateStatus(
            @Parameter(description = "ID de la postulación") @PathVariable Long id,
            @Parameter(description = "Nuevo estado de la aplicación") @RequestParam ApplicationStatus status,Principal principal) {
        JobApplicationResponse response = jobApplicationService.updateStatus(id, status,principal.getName());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Mis postulaciones", description = "Devuelve el historial de todas las aplicaciones realizadas por el candidato autenticado.")
    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/my-applications")
    public ResponseEntity<List<JobApplicationResponse>> getMyApplications(Principal principal) {
        return ResponseEntity.ok(jobApplicationService.getHistoryByCandidate(principal.getName()));
    }

    @Operation(summary = "Postulaciones por oferta", description = "Devuelve todas las aplicaciones recibidas para una oferta en particular.")
    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/by-offer/{offerId}")
    public ResponseEntity<List<JobApplicationResponse>> getApplicationsByOffer(@PathVariable Long offerId, Principal principal) {
        return ResponseEntity.ok(jobApplicationService.getHistoryByOffer(offerId, principal.getName()));
    }

    @Operation(summary = "Ranking de candidatos", description = "Devuelve las postulaciones de una oferta ordenadas de mayor a menor según el Match Score de la IA.")
    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/{jobOfferId}/ranking")
    public ResponseEntity<List<JobApplicationResponse>> getRanking(@PathVariable Long jobOfferId){
        return new ResponseEntity<>(jobApplicationService.getRankingMoreCompatibility(jobOfferId), HttpStatus.OK);
    }

    @Operation(summary = "Disparar análisis de IA", description = "Ejecuta el LLM para contrastar el perfil del candidato, extraer texto de su PDF y calcular la compatibilidad (Match Score).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Análisis completado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error de comunicación con Groq/OpenAI")

    })
    @PreAuthorize("hasRole('RECRUITER')")
    @PatchMapping("/{id}/analyze-difference")
    public ResponseEntity<JobApplicationResponse> analyzeDifference(@Parameter(description = "ID de la postulación") @PathVariable Long id, Principal principal) {
        return new ResponseEntity<>(analysisService.analizeDifference(id, principal.getName()), HttpStatus.OK);
    }

    @Operation(summary = "Subir Currículum en PDF", description = "Carga y almacena localmente el archivo PDF con el CV del candidato para esta postulación específica.")
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping(value = "/{id}/upload-cv", consumes = "multipart/form-data")
    public ResponseEntity<JobApplicationResponse> uploadCv(
            @Parameter(description = "ID de la postulación") @PathVariable Long id,
            @Parameter(description = "Archivo PDF del currículum") @RequestParam("file") org.springframework.web.multipart.MultipartFile file,
            Principal principal) {
        JobApplicationResponse response = jobApplicationService.uploadCv(id, file, principal.getName());
        return ResponseEntity.ok(response);
    }
}