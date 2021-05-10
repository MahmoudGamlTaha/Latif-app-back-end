package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.UserRole;
import com.commerce.backend.model.request.role.UserRoleRequest;
import com.commerce.backend.model.request.role.UserRoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;

public interface UserRoleCacheService {

    BasicResponse create (UserRoleRequest userRole) throws Exception;
    UserRole update (UserRoleRequestUpdate userRole) throws Exception;
    RoleResponse getUserRoleByUserId(Long id) throws Exception;
    BasicResponse delete(Long id) throws Exception;
}
