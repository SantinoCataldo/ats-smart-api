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

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/companies")
@Tag(name = "Empresas", description = "Endpoints para el registro, edición y consulta de empresas corporativas")
public class CompanyController {
    private final CompanyService companyService;

    @Operation(summary = "Registrar una nueva empresa", description = "Añade una organización corporativa a la base de datos central asegurando un nombre único.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Nombre corporativo duplicado o URLs con formato inválido")
    })

    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request){
        return new ResponseEntity<>(companyService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Modificar datos corporativos", description = "Actualiza la descripción, sitio web o enlace del logotipo de la organización por su ID.")

    @PreAuthorize("hasAnyRole('ADMIN', 'RECRUITER')")
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@Parameter(description = "ID único de la empresa") @PathVariable Long id,
                                                  @Valid @RequestBody CompanyRequest request){
        return new ResponseEntity<>(companyService.update(id, request), HttpStatus.OK);
    }

    @Operation(summary = "Obtener detalle corporativo", description = "Devuelve toda la información pública disponible de una empresa por ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@Parameter(description = "ID único de la empresa") @PathVariable Long id){
        return new ResponseEntity<>(companyService.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Listar todas las empresas", description = "Devuelve el catálogo de empresas registradas completo en el ecosistema de empleo.")
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll(){
        return new ResponseEntity<>(companyService.getAll(), HttpStatus.OK);
    }
}