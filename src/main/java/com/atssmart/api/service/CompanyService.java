package com.atssmart.api.service;

import com.atssmart.api.dto.request.CompanyRequest;
import com.atssmart.api.dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {
    CompanyResponse create(CompanyRequest request);
    CompanyResponse getById(Long id);
    List<CompanyResponse> getAll();
    CompanyResponse update(Long id, CompanyRequest request);
    public void delete(Long id);
}
