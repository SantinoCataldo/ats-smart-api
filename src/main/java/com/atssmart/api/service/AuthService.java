package com.atssmart.api.service;

import com.atssmart.api.dto.request.AuthRequest;
import com.atssmart.api.dto.request.RegisterRequest;
import com.atssmart.api.dto.response.AuthResponse;
import com.atssmart.api.enums.UserRole;
import com.atssmart.api.enums.UserStatus;
import com.atssmart.api.model.CandidateProfileEntity;
import com.atssmart.api.model.RecruiterProfileEntity;
import com.atssmart.api.model.UserEntity;
import com.atssmart.api.repository.UserRepository;
import com.atssmart.api.securityJwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.findUserEntityByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .status(UserStatus.ACTIVE) //
                .build();

        if (request.role() == UserRole.ROLE_CANDIDATE) {
            CandidateProfileEntity profile = new CandidateProfileEntity();
            profile.setFullName(request.fullName());
            profile.setUserEntity(user);
            user.setCandidateProfile(profile);
        } else if (request.role() == UserRole.ROLE_RECRUITER) {
            RecruiterProfileEntity profile = new RecruiterProfileEntity();
            profile.setFullName(request.fullName());
            profile.setUserEntity(user);
            user.setRecruiterProfile(profile);
        }

        userRepository.save(user);
    }

    public UserDetails authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findUserEntityByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + input.getEmail()));
    }

    @Transactional
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        com.atssmart.api.model.UserEntity user = userRepository.findUserEntityByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh token does not match");
        }

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Refresh token expired or invalid");
        }

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

}