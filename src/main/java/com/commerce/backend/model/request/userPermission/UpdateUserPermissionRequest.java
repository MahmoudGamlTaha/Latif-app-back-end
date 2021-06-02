package com.commerce.backend.model.request.userPermission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserPermissionRequest {

    private Long id;

    private String httpMethod;

    private String httpPath;
}
