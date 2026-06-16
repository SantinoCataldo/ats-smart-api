package com.atssmart.api.service;


import com.atssmart.api.dto.request.RecruiterProfileRequest;
import com.atssmart.api.dto.response.RecruiterProfileResponse;
import com.atssmart.api.enums.UserRole;
import com.atssmart.api.exception.ResourceNotFoundException;
import com.atssmart.api.mapper.RecruiterProfileMapper;
import com.atssmart.api.model.CompanyEntity;
import com.atssmart.api.model.RecruiterProfileEntity;
import com.atssmart.api.model.UserEntity;
import com.atssmart.api.repository.CompanyRepository;
import com.atssmart.api.repository.RecruiterProfileRepository;
import com.atssmart.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruiterProfileServiceImpl implements RecruiterProfileService{
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final RecruiterProfileMapper recruiterProfileMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public RecruiterProfileResponse create(RecruiterProfileRequest request){
        if (recruiterProfileRepository.existsByUserEntityId(request.getUserId())) {
            throw new IllegalArgumentException("Este usuario ya tiene un perfil de reclutador " + request.getUserId());
        }

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        CompanyEntity company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.getCompanyId()));

        RecruiterProfileEntity recruiter =  new RecruiterProfileEntity();
        recruiter.setFullName(request.getFullName());
        recruiter.setCompanyRole(request.getCompanyRole());
        recruiter.setUserEntity(user);
        recruiter.setCompany(company);

        return recruiterProfileMapper.toResponse(recruiterProfileRepository.save(recruiter));
    }

    @Override
    @Transactional
    public RecruiterProfileResponse update(Long id, RecruiterProfileRequest request, String email){
        RecruiterProfileEntity recruiter = recruiterProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter", "id", id));

        UserEntity currentUser = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));


        if (currentUser.getRole() == UserRole.ROLE_RECRUITER) {
            if (recruiter.getUserEntity() == null || !recruiter.getUserEntity().getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("Acceso denegado: No puedes editar el perfil de otro reclutador.");
            }
        }

        CompanyEntity company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.getCompanyId()));

        recruiter.setFullName(request.getFullName());
        recruiter.setCompanyRole(request.getCompanyRole());
        recruiter.setCompany(company);

        RecruiterProfileEntity updated = recruiterProfileRepository.save(recruiter);
        return recruiterProfileMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecruiterProfileResponse> getAll(){
        return recruiterProfileRepository.findAll().stream().map(recruiterProfileMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecruiterProfileResponse getById(Long id){
        RecruiterProfileEntity recruiter = recruiterProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter", "id", id));

        return recruiterProfileMapper.toResponse(recruiter);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!recruiterProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recruiter", "id", id);
        }
        recruiterProfileRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public RecruiterProfileResponse getProfile(String email) {
        RecruiterProfileEntity recruiter = recruiterProfileRepository.findByUserEntityEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter", "email", email));
        return recruiterProfileMapper.toResponse(recruiter);
    }

    @Override
    @Transactional
    public RecruiterProfileResponse updateProfile(RecruiterProfileRequest request, String email) {
        RecruiterProfileEntity recruiter = recruiterProfileRepository.findByUserEntityEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter", "email", email));

        CompanyEntity company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.getCompanyId()));

        recruiter.setFullName(request.getFullName());
        recruiter.setCompanyRole(request.getCompanyRole());
        recruiter.setCompany(company);

        return recruiterProfileMapper.toResponse(recruiterProfileRepository.save(recruiter));
    }
}
