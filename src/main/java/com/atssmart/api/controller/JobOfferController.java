package com.atssmart.api.controller;

import com.atssmart.api.dto.request.JobOfferRequest;
import com.atssmart.api.dto.response.JobOfferResponse;
import com.atssmart.api.enums.JobOfferModality;
import com.atssmart.api.service.JobOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/job-offers")
@RequiredArgsConstructor
@Tag(name = "Ofertas Laborales", description = "Endpoints para la gestión, publicación y búsqueda de ofertas de empleo")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    @Operation(summary = "Crear oferta laboral", description = "Publica una nueva vacante de empleo. Requiere rol RECLUTADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Oferta creada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
    })
    @PostMapping
    public ResponseEntity<JobOfferResponse> create(@Valid @RequestBody JobOfferRequest request, Principal principal) {
        JobOfferResponse response = jobOfferService.create(request, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar oferta laboral", description = "Modifica los datos de una oferta existente por su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<JobOfferResponse> update(@Parameter(description = "ID de la oferta") @PathVariable Long id,
                                                   @Valid @RequestBody JobOfferRequest request, Principal principal) {
        JobOfferResponse response = jobOfferService.update(id, request, principal.getName());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener oferta por ID", description = "Devuelve el detalle completo de una oferta laboral.")
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponse> getById(@Parameter(description = "ID de la oferta") @PathVariable Long id) {
        JobOfferResponse response = jobOfferService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar oferta", description = "Elimina físicamente una oferta laboral del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID de la oferta") @PathVariable Long id, Principal principal) {
        jobOfferService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar y filtrar ofertas", description = "Lista las ofertas laborales activas utilizando múltiples filtros opcionales.")
    @GetMapping("/search")
    public ResponseEntity<List<JobOfferResponse>> search(
            @Parameter(description = "Parte del título a buscar") @RequestParam(required = false) String title,
            @Parameter(description = "Sector (Ej: Tecnologia)") @RequestParam(required = false) String sector,
            @Parameter(description = "Ubicación geográfica") @RequestParam(required = false) String location,
            @Parameter(description = "Modalidad de trabajo") @RequestParam(required = false) JobOfferModality modality,
            @Parameter(description = "Lista de IDs de habilidades requeridas") @RequestParam(required = false) List<Long> skillIds) {
        List<JobOfferResponse> response = jobOfferService.search(title, sector, location, modality, skillIds);
        return ResponseEntity.ok(response);
    }
}