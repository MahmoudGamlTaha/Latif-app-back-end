package com.commerce.backend.service;

import com.commerce.backend.model.response.BasicResponse;

public interface ReportReasonsService {
    BasicResponse getReasons();

    BasicResponse createReason(String reason, String reasonAr);

    BasicResponse updateReason(Long id, String reason, String reasonAr);

    BasicResponse removeReason(Long id);
}
