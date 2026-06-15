package com.atssmart.api.service;


import com.atssmart.api.dto.request.UserRequest;
import com.atssmart.api.dto.response.UserResponse;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.UserMapper;
import com.atssmart.api.model.UserEntity;
import com.atssmart.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email ya usado " + request.getEmail());
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse update(Long id, UserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (!user.getEmail().equalsIgnoreCase(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + request.getEmail());
        }

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        UserEntity updated = userRepository.save(user);
        return userMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public List<UserResponse> getAll(){
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }


    @Override
    @Transactional
    public UserResponse getById(Long id){
        UserEntity userr = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        return userMapper.toResponse(userr);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recruiter", "id", id);
        }
        userRepository.deleteById(id);
    }
}
