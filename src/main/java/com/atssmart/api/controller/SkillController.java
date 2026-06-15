package com.atssmart.api.controller;

import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.enums.SkillCategory;
import com.atssmart.api.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@Tag(name = "Habilidades (Skills)", description = "Endpoints para administrar el catálogo global de aptitudes técnicas y laborales")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @Operation(summary = "Registrar nueva habilidad", description = "Agrega una habilidad única al catálogo. Requiere rol de Administrador.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Habilidad registrada de forma exitosa"),
            @ApiResponse(responseCode = "400", description = "Ya existe una habilidad registrada con el mismo nombre")
    })
    @PostMapping
    public ResponseEntity<SkillResponse> create(@Valid @RequestBody SkillRequest request){
        return new ResponseEntity<>(skillService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar habilidades del catálogo", description = "Obtiene la lista completa de habilidades, permitiendo filtrar de forma opcional por su categoría sectorial.")
    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAll(
            @Parameter(description = "Categoría sectorial de filtrado (opcional)") @RequestParam(required = false) SkillCategory category){
        List<SkillResponse> skills;
        if (category != null) {
            skills = skillService.getByCategory(category);
        } else {
            skills = skillService.getAll();
        }
        return ResponseEntity.ok(skills);
    }

    @Operation(summary = "Obtener habilidad por ID", description = "Consulta los detalles de una habilidad específica a través de su identificador.")
    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getById(@Parameter(description = "ID único de la habilidad") @PathVariable Long id){
        return ResponseEntity.ok(skillService.getById(id));
    }

    @Operation(summary = "Modificar una habilidad", description = "Actualiza el nombre descriptivo o la categoría de una habilidad del catálogo existente.")
    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> update(@Parameter(description = "ID de la habilidad a modificar") @PathVariable Long id,
                                                @Valid @RequestBody SkillRequest request){
        return ResponseEntity.ok(skillService.update(id, request));
    }

    @Operation(summary = "Eliminar una habilidad", description = "Remueve permanentemente del catálogo general una habilidad específica.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID de la habilidad a dar de baja") @PathVariable Long id){
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}