package com.commerce.backend.model.request.subscription;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class SubscriptionTypeRequest {

    @Size(min = 3, max = 26)
    private String name;

    private Integer adsNumber;

    @Positive
    private Integer periodInDays;

    private String description;
    
    private String descriptionAr;
    
    private Float price;
}
