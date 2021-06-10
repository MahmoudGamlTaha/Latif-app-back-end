package com.commerce.backend.service;

import com.commerce.backend.model.request.subscription.SubscriptionTypeRequest;
import com.commerce.backend.model.response.BasicResponse;

public interface SubscriptionTypesService {
    BasicResponse findAll(Integer page, Integer size);
    BasicResponse findById(Long id);
    BasicResponse create(SubscriptionTypeRequest request);
    BasicResponse delete(Long id);
    BasicResponse update(SubscriptionTypeRequest request, Long id);
    
}
