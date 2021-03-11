package com.commerce.backend.service;

import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.dao.UserRoleRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserRole;
import com.commerce.backend.model.request.role.UserRoleRequest;
import com.commerce.backend.model.response.role.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository repo;
    private final UserRepository userRepo;
    private final RoleServiceImpl roleService;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository repo, UserRepository userRepo, RoleServiceImpl roleService) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.roleService = roleService;
    }

    @Override
    public UserRole create(UserRoleRequest userRole) throws Exception {
        try {
            return UserRole.builder()
                    .userId(userRole.getUserId())
                    .roleId(userRole.getRoleId())
                    .build();
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    @Override
    public UserRole update(UserRole userRole) throws Exception {
        try {
            UserRole ur = repo.findById(userRole.getId()).orElse(null);
            assert ur != null;
            if(userRole.getRoleId() != null)
            {
                ur.setRoleId(userRole.getRoleId());
            }

            if(userRole.getUserId() != null)
            {
                ur.setUserId(userRole.getUserId());
            }
            return repo.save(ur);
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    @Override
    public RoleResponse getUserRoleByUserId(Long id)
    {
        Long roleId = repo.getUserRoleByUserId(id).getRoleId();
        return roleService.getRoleById(roleId);
    }

    @Override
    public String delete(Long id) throws Exception {
        try {
            assert id != null;
            repo.deleteById(id);
            return "removed";
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }
}
