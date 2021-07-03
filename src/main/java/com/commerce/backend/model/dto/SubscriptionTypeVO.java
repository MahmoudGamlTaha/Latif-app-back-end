package com.commerce.backend.model.dto;

import com.commerce.backend.constants.PeriodUnit;
import lombok.Data;


@Data
public class SubscriptionTypeVO {

    private Long id;

    private String name;

    private Integer adsNumber;

    private Integer periodInDays;

    private Integer numberUser;

    private String descriptionAr;
    
    private String description;

    private Float price;

    private PeriodUnit periodUnit;
}
