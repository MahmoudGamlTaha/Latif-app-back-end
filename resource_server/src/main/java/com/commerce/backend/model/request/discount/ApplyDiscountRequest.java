package com.commerce.backend.model.request.discount;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class ApplyDiscountRequest {
    @Setter(AccessLevel.PUBLIC)
    @NotBlank
    private String code;
}
