package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.request.role.RoleRequest;
import com.commerce.backend.model.request.role.RoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;

import java.util.List;

public interface RoleCacheService {
    List<Role> getRolesList();
    RoleResponse getRoleById(Long id);
    Role createRole(RoleRequest role) throws Exception;
    Role update(RoleRequestUpdate role) throws Exception;
    BasicResponse delete (Long id);
}
