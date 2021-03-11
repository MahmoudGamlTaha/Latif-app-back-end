package com.commerce.backend.service;

import com.commerce.backend.model.entity.UserRole;
import com.commerce.backend.model.request.role.UserRoleRequest;
import com.commerce.backend.model.response.role.RoleResponse;

public interface UserRoleService {

    UserRole create (UserRoleRequest userRole) throws Exception;
    UserRole update (UserRole userRole) throws Exception;
    RoleResponse getUserRoleByUserId(Long id);
    String delete(Long id) throws Exception;

}
