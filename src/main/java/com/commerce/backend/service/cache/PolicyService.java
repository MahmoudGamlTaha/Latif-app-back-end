package com.commerce.backend.service.cache;

import org.springframework.stereotype.Service;
import com.commerce.backend.model.entity.Policy;
@Service
public interface PolicyService {
    Policy find();
    Policy updatePolicy();
}
