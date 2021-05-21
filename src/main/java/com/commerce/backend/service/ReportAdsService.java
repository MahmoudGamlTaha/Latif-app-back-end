package com.commerce.backend.service;

import com.commerce.backend.constants.ReportType;
import com.commerce.backend.model.request.report.ReportRequest;
import com.commerce.backend.model.response.BasicResponse;

public interface ReportAdsService {
    BasicResponse getAll(Integer page, Integer pageSize);

    BasicResponse create(ReportRequest request);

    BasicResponse getReportedAds(ReportType type,Integer page, Integer pageSize);

    BasicResponse removeFromInterestedList(Long id);
}
