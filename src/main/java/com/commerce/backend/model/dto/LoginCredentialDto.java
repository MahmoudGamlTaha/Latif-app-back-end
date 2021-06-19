
package com.commerce.backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginCredentialDto {

    @NotNull
    @Size(min = 11)
    private String mobile;

    @NotNull
    @Size(min = 4)
    private String password;
    
    private String token;
    
    private String device;
}