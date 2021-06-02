package com.commerce.backend.model.request;

import lombok.Data;

@Data
public class AssignUserPermissionRequest {
    private Long roleId;
    private Long permissionId;
}
