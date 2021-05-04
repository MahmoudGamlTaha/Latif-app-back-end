package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.response.BasicResponse;

public interface DashboardService {
    BasicResponse getDashboard();
    BasicResponse getAdsCountByCategoryId(Long id);
}
