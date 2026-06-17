package com.atssmart.api.service;

import com.atssmart.api.dto.request.JobApplicationRequest;
import com.atssmart.api.dto.response.JobApplicationResponse;
import com.atssmart.api.enums.ApplicationStatus;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * Service interface for managing Job Applications.
 */
public interface JobApplicationService {
    JobApplicationResponse apply(JobApplicationRequest request, String userEmail);
    JobApplicationResponse updateStatus(Long id, ApplicationStatus status, String userEmail);
    List<JobApplicationResponse> getHistoryByCandidate(String userEmail);
    List<JobApplicationResponse> getHistoryByOffer(Long jobOfferId, String userEmail);
    List<JobApplicationResponse> getRankingMoreCompatibility(Long jobOfferId);
    JobApplicationResponse uploadCv(Long id, MultipartFile file, String email);
}
