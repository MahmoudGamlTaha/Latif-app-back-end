package com.commerce.backend.model.request.userPermission;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserPermissionRequest {

    @NotBlank
    private String httpMethod;

    @NotBlank
    private String httpPath;
}
