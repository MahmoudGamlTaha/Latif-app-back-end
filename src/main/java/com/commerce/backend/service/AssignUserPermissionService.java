package com.commerce.backend.service;

import com.commerce.backend.model.request.AssignUserPermissionRequest;
import com.commerce.backend.model.response.BasicResponse;

import java.util.List;

public interface AssignUserPermissionService {
    BasicResponse getAll(Long roleId);

    BasicResponse create(AssignUserPermissionRequest assignUserPermission);

    BasicResponse remove(AssignUserPermissionRequest assignUserPermission);
}
