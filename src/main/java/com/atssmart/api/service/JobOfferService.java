package com.atssmart.api.service;

import com.atssmart.api.dto.request.JobOfferRequest;
import com.atssmart.api.dto.response.JobOfferResponse;
import com.atssmart.api.enums.JobOfferModality;
import java.util.List;

/**
 * Service interface for managing Job Offers.
 */
public interface JobOfferService {
    JobOfferResponse create(JobOfferRequest request, String recruiterEmail);
    JobOfferResponse update(Long id, JobOfferRequest request, String recruiterEmail);
    JobOfferResponse getById(Long id);
    void delete(Long id, String recruiterEmail);
    List<JobOfferResponse> search(String title, String sector, String location, JobOfferModality modality, List<Long> skillIds);
}
