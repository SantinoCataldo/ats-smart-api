package com.atssmart.api.service;

import com.atssmart.api.dto.request.CompanyRequest;
import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.CompanyResponse;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.mapper.CompanyMapper;
import com.atssmart.api.mapper.SkillMapper;
import com.atssmart.api.model.CompanyEntity;
import com.atssmart.api.model.SkillEntity;
import com.atssmart.api.repository.CompanyRepository;
import com.atssmart.api.repository.SkillRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.CompositeName;

@AllArgsConstructor
@Service
public class CompanyServiceImpl {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional
    public CompanyResponse create(CompanyRequest request) {
            if (companyRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Ya existe una compania con el nombre: " + request.getName());
        }
        CompanyEntity company = companyMapper.toEntity(request);
        CompanyEntity saved = companyRepository.save(company);
        return companyMapper.toResponse(saved);
    }
}
