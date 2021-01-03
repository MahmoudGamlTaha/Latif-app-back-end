package com.commerce.backend.model.request.cart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Setter(AccessLevel.PUBLIC)
public class AddToCartRequest {
  
    @NotNull
    @Min(1)
    private Long productVariantId;

    @NotNull
    @Min(1)
    private Integer amount;
}
