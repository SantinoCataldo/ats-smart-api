package com.atssmart.api.service;

import com.atssmart.api.dto.response.JobApplicationResponse;

public interface AnalysisService {
    JobApplicationResponse analizeDifference(Long JobApplicationId);
}
