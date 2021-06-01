package com.commerce.backend.service;

import com.commerce.backend.model.response.BasicResponse;

public interface ReportReasonsService {
    BasicResponse getReasons();

    BasicResponse createReason(String reason);

    BasicResponse updateReason(Long id, String reason);

    BasicResponse removeReason(Long id);
}
