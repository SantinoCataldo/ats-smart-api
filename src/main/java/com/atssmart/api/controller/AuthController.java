package com.atssmart.api.controller;

import com.atssmart.api.dto.request.AuthRequest;
import com.atssmart.api.dto.request.RegisterRequest;
import com.atssmart.api.dto.request.RefreshTokenRequest;
import com.atssmart.api.dto.response.AuthResponse;
import com.atssmart.api.service.AuthService;
import com.atssmart.api.securityJwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro, inicio de sesión y gestión de tokens")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea una nueva cuenta para un candidato o reclutador.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o el email ya se encuentra en uso")
    })
    @SecurityRequirements()
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario y devuelve los tokens JWT de acceso y refresco.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @SecurityRequirements()
    @PostMapping()
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        UserDetails userDetails = authService.authenticate(authRequest);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @Operation(summary = "Refrescar token", description = "Genera un nuevo token de acceso a partir de un refresh token válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token renovado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Refresh token inválido o expirado")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshAccessToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }
}