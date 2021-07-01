package com.commerce.backend.model.request.user;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserAddressRequest {
  
    private Long city;

    private String zip;

    @Pattern(regexp = "[0-9a-zA-Z #,-]+")
    @Size(min = 3, max = 240)
    private String address;
}
