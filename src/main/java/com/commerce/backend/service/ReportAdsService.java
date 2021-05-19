package com.commerce.backend.service;

import com.commerce.backend.model.request.report.ReportRequest;
import com.commerce.backend.model.response.BasicResponse;

public interface ReportAdsService {
    BasicResponse getAll(Integer page, Integer pageSize);

    BasicResponse makeReport(ReportRequest request);

    BasicResponse getInterestedAds(Integer page, Integer pageSize);

    BasicResponse getReportedAds(Integer page, Integer pageSize);

    BasicResponse removeFromInterestedList(Long id);
}
