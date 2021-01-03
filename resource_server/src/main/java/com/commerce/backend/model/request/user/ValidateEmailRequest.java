package com.commerce.backend.model.request.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Setter(AccessLevel.PUBLIC)
public class ValidateEmailRequest {
    @NotBlank
    String token;
}
