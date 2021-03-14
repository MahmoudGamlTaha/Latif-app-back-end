package com.commerce.backend.service;

import com.commerce.backend.converter.role.RoleResponseConverter;
import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.request.role.RoleRequest;
import com.commerce.backend.model.request.role.RoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;
import com.commerce.backend.service.cache.RoleCacheServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleCacheServiceImpl cacheService;
    private final RoleResponseConverter converter;

    public RoleServiceImpl(RoleCacheServiceImpl cacheService, RoleResponseConverter converter) {
        this.cacheService = cacheService;
        this.converter = converter;
    }


    @Override
    public List<RoleResponse> getRolesList() {
        List<Role> roles = cacheService.getRolesList();
        return roles
                .stream()
                .map(converter)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        return cacheService.getRoleById(id);
    }

    @Override
    public RoleResponse createRole(RoleRequest role) throws Exception {
        Role roles = cacheService.createRole(role);
        return converter.apply(roles);
    }

    @Override
    public RoleResponse update(RoleRequestUpdate role) throws Exception {
        Role roles = cacheService.update(role);
        return converter.apply(roles);
    }

    @Override
    public BasicResponse delete(Long id) {
        return cacheService.delete(id);
    }
}
