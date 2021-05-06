package com.commerce.backend.model.request.subscription;
import lombok.Data;

@Data
public class UserSubscriptionRequest {

    private Long userId;

    private Long subscriptionId;
}
