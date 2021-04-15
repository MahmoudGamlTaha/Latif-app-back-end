package com.commerce.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAccVO extends UserAdsVO {
    private Boolean used;
    private double stock;
}
