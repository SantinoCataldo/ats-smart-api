package com.atssmart.api.controller;

import com.atssmart.api.dto.request.AuthRequest;
import com.atssmart.api.dto.request.RefreshTokenRequest;
import com.atssmart.api.dto.response.AuthResponse;
import com.atssmart.api.service.AuthService;
import com.atssmart.api.securityJwt.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping()
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        UserDetails userDetails = authService.authenticate(authRequest);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshAccessToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }

}
