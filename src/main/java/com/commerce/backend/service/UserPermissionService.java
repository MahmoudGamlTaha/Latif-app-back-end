package com.commerce.backend.service;

import com.commerce.backend.model.request.userPermission.UpdateUserPermissionRequest;
import com.commerce.backend.model.request.userPermission.UserPermissionRequest;
import com.commerce.backend.model.response.BasicResponse;

import java.util.List;

public interface UserPermissionService {
    BasicResponse getAll(Integer page, Integer pageSize);

    BasicResponse create(List<UserPermissionRequest> userPermission);

    BasicResponse update(List<UpdateUserPermissionRequest> userPermission);

    BasicResponse remove(Long id);
}
