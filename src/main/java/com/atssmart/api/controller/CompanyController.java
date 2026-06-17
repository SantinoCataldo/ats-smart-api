package com.atssmart.api.controller;

import com.atssmart.api.dto.request.CompanyRequest;
import com.atssmart.api.dto.response.CompanyResponse;
import com.atssmart.api.service.CompanyService;
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
@RequestMapping("/api/companies")
@Tag(name = "Empresas", description = "Endpoints para el registro, edición y consulta de empresas corporativas")
public class CompanyController {
    private final CompanyService companyService;

    @Operation(summary = "Registrar una nueva empresa", description = "Añade una organización corporativa a la base de datos central.")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.create(request));
    }

    @Operation(summary = "Modificar datos corporativos", description = "Actualiza la descripción, sitio web o enlace del logotipo.")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@Parameter(description = "ID único de la empresa") @PathVariable Long id,
                                                  @Valid @RequestBody CompanyRequest request,
                                                  Principal principal){
        return ResponseEntity.ok(companyService.update(id, request, principal.getName()));
    }

    @Operation(summary = "Obtener detalle corporativo", description = "Devuelve toda la información pública disponible de una empresa por ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@Parameter(description = "ID único de la empresa") @PathVariable Long id){
        return ResponseEntity.ok(companyService.getById(id));
    }

    @Operation(summary = "Listar todas las empresas", description = "Devuelve el catálogo de empresas registradas completo en el ecosistema de empleo.")
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll(){
        return ResponseEntity.ok(companyService.getAll());
    }
}