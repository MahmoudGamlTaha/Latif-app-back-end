package com.commerce.backend.service;

import com.commerce.backend.model.request.subscription.UserSubscriptionRequest;
import com.commerce.backend.model.response.BasicResponse;

public interface UserSubscriptionService {
    BasicResponse findByUserId(Long id, Integer page, Integer size);
    BasicResponse findById(Long id);
    BasicResponse findBySubscriptionId(Long id, Integer page, Integer size);
    BasicResponse create(UserSubscriptionRequest request);
    BasicResponse update(UserSubscriptionRequest request, Long id);
    BasicResponse delete(Long id);
}
