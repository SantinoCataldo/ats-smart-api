package com.atssmart.api.service;

import com.atssmart.api.dto.request.CompanyRequest;
import com.atssmart.api.dto.request.SkillRequest;
import com.atssmart.api.dto.response.CompanyResponse;
import com.atssmart.api.dto.response.SkillResponse;
import com.atssmart.api.exception.ResourceNotFoundException;
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
import java.util.List;

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

    public CompanyResponse update(Long id , CompanyRequest request){
        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));

        if (!company.getName().equalsIgnoreCase(request.getName())
                && companyRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Ya existe una compania con el nombre: " + request.getName());
        }

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLogoUrl(request.getLogoUrl());

        CompanyEntity update = companyRepository.save(company);
        return companyMapper.toResponse(update);
    }

    public List<CompanyResponse> getAll(){
        return companyRepository.findAll().stream().map(companyMapper::toResponse).toList();
    }

    public CompanyResponse getById(Long id){
        CompanyEntity company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        return companyMapper.toResponse(company);
    }
}
