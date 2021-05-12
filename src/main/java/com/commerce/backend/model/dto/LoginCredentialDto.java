package com.commerce.backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginCredentialDto {

    @NotNull
    private String mobile;

    @NotNull
    @Size(min = 1)
    private String password;
}