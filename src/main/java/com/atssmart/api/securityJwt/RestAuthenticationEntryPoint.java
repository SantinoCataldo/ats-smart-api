package com.atssmart.api.securityJwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String errorMessage = switch (authException) {
            case BadCredentialsException e -> "Invalid credentials";
            case DisabledException e -> "Account disabled";
            case LockedException e -> "Account locked";
            case AccountExpiredException e -> "Account expired";
            case CredentialsExpiredException e -> "Credentials expired";
            case InsufficientAuthenticationException e -> "Insufficient authentication status";
            case AuthenticationServiceException e -> "Authentication service error";
            default -> "Authentication error: " + authException.getMessage();
        };
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("status", HttpStatus.UNAUTHORIZED.value());
        errorBody.put("message", errorMessage);
        errorBody.put("path", request.getRequestURI());
        errorBody.put("timestamp", LocalDateTime.now());
        String jsonResponse = objectMapper.writeValueAsString(errorBody);

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}