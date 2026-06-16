package com.atssmart.api.controller;

import com.atssmart.api.dto.request.RecruiterProfileRequest;
import com.atssmart.api.dto.response.RecruiterProfileResponse;
import com.atssmart.api.service.RecruiterProfileService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recruiter-profiles")
@Tag(name = "Perfiles de Reclutadores", description = "Endpoints para la gestión administrativa de los perfiles asociados a Reclutadores")
public class RecruiterProfileController {
    private final RecruiterProfileService recruiterProfileService;

    @Operation(summary = "Asociar perfil de reclutador", description = "Crea la entidad de perfil para un usuario vinculándolo directamente a una empresa activa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Perfil asociado con éxito"),
            @ApiResponse(responseCode = "400", description = "El identificador de usuario seleccionado ya cuenta con un perfil de reclutador activo")
    })

    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    @PostMapping
    public ResponseEntity<RecruiterProfileResponse> create(@Valid @RequestBody RecruiterProfileRequest request){
        return new ResponseEntity<>(recruiterProfileService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar perfil de reclutador", description = "Modifica los datos personales o cambia la empresa asociada del reclutador.")

    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    @PutMapping("/{id}")
    public ResponseEntity<RecruiterProfileResponse> update(@Parameter(description = "ID del perfil de reclutador") @PathVariable Long id,
                                                           @Valid @RequestBody RecruiterProfileRequest request){
        return new ResponseEntity<>(recruiterProfileService.update(id, request), HttpStatus.OK);
    }

    @Operation(summary = "Dar de baja perfil de reclutador", description = "Elimina físicamente el perfil administrativo de un reclutador del sistema.")

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID del perfil de reclutador") @PathVariable Long id){
        recruiterProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos los reclutadores", description = "Retorna de manera exhaustiva el listado completo de perfiles de reclutamiento.")
    @GetMapping
    public ResponseEntity<List<RecruiterProfileResponse>> getAll(){
        return new ResponseEntity<>(recruiterProfileService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener reclutador por ID", description = "Recupera la ficha individual y de vinculación empresarial de un reclutador.")
    @GetMapping("/{id}")
    public ResponseEntity<RecruiterProfileResponse> getById(@Parameter(description = "ID del perfil de reclutador") @PathVariable Long id){
        return new ResponseEntity<>(recruiterProfileService.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener mi perfil de reclutador", description = "Devuelve el perfil del reclutador autenticado.")
    @GetMapping("/me")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<RecruiterProfileResponse> getMyProfile(Principal principal) {
        return ResponseEntity.ok(recruiterProfileService.getProfile(principal.getName()));
    }

    @Operation(summary = "Actualizar mi perfil de reclutador", description = "Permite al reclutador modificar sus datos de perfil y asociar su empresa.")
    @PutMapping("/me")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<RecruiterProfileResponse> updateMyProfile(
            @Valid @RequestBody RecruiterProfileRequest request,
            Principal principal) {
        return ResponseEntity.ok(recruiterProfileService.updateProfile(request, principal.getName()));
    }
}