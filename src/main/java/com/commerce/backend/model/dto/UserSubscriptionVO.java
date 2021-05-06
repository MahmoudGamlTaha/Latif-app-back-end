package com.commerce.backend.model.dto;

import com.commerce.backend.model.response.user.UserResponse;
import lombok.Data;

import java.util.Date;

@Data
public class UserSubscriptionVO {

    private Long id;

    private UserResponse user;

    private SubscriptionTypeVO subscription;

    private Date startDate;

    private Date endDate;

    private Date createdAt;

    private Date updatedAt;
}
