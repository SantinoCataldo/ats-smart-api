package com.atssmart.api.service;

import com.atssmart.api.dto.request.RegisterRequestDTO;
import com.atssmart.api.dto.response.AuthResponseDTO;
import com.atssmart.api.enums.UserRole;
import com.atssmart.api.model.RoleEntity;
import com.atssmart.api.model.UserEntity;
import com.atssmart.api.repository.RoleRepository;
import com.atssmart.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl userDetailsService;
    private final JwtService jwtService;

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {


        if (userRepository.findUserEntityByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }

        RoleEntity userRole = roleRepository.findByRoleEnum(UserRole.ROLE_USER)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No existe el rol USER inicial"));


        UserEntity newUser = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.ROLE_USER)
                .build();

        userRepository.save(newUser);


        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
        String jwt = jwtService.generateToken(userDetails);

        return new AuthResponseDTO("Bearer", jwt);
    }
}