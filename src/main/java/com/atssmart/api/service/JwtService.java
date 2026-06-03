package com.atssmart.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service

public interface JwtService {

    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean validateRefreshToken(String refreshToken, UserDetails userDetails);
}
