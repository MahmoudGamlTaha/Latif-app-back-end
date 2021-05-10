package com.commerce.backend.service;

import com.commerce.backend.dao.UserRoleRepository;
import com.commerce.backend.model.entity.UserRole;
import com.commerce.backend.model.request.role.UserRoleRequest;
import com.commerce.backend.model.request.role.UserRoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;
import com.commerce.backend.service.cache.UserRoleCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleCacheServiceImpl cacheService;

    @Autowired
    public UserRoleServiceImpl(UserRoleCacheServiceImpl cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public BasicResponse create(UserRoleRequest userRole) throws Exception {
        try {
            return cacheService.create(userRole);
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    @Override
    public UserRole update(UserRoleRequestUpdate userRole) throws Exception {
        try {
            return cacheService.update(userRole);
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    @Override
    public RoleResponse getUserRoleByUserId(Long id) throws Exception {
        return cacheService.getUserRoleByUserId(id);
    }

    @Override
    public BasicResponse delete(Long id) throws Exception {
        try {
            return cacheService.delete(id);
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }
}
