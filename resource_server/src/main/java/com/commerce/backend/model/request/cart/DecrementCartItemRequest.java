package com.commerce.backend.model.request.cart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DecrementCartItemRequest {
    @Setter(AccessLevel.PUBLIC)
	@NotNull
    @Min(1)
    private Long cartItemId;
   
    @Setter(AccessLevel.PUBLIC)
    @NotNull
    @Min(1)
    private Integer amount;
}
