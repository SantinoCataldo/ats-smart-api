package com.atssmart.api.controller;

import com.atssmart.api.dto.request.UserRequest;
import com.atssmart.api.dto.response.UserResponse;
import com.atssmart.api.service.UserService;
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

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para el ABM y auditoría global de usuarios (Administrador)")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Crear un usuario", description = "Registra manualmente una nueva credencial de usuario en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "El correo electrónico ya se encuentra registrado")
    })
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los usuarios", description = "Obtiene el listado general de cuentas registradas con fines de auditoría.")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve los datos de una cuenta de usuario específica mediante su identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@Parameter(description = "ID único del usuario") @PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "Actualizar usuario", description = "Modifica el correo electrónico, contraseña o rol de un usuario existente.")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@Parameter(description = "ID del usuario a modificar") @PathVariable Long id,
                                               @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @Operation(summary = "Eliminar usuario", description = "Remueve permanentemente un usuario del sistema por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID del usuario a eliminar") @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}