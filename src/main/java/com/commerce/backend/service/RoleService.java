package com.commerce.backend.service;

import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.request.role.RoleRequest;
import com.commerce.backend.model.request.role.RoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleResponse> getRolesList();
    RoleResponse getRoleById(Long id);
    RoleResponse createRole(RoleRequest role) throws Exception;
    RoleResponse update(RoleRequestUpdate role) throws Exception;
    BasicResponse delete(Long id);
}
