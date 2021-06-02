package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.dao.RoleRepository;
import com.commerce.backend.dao.UserPermissionsRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.UserPermissions;
import com.commerce.backend.model.request.AssignUserPermissionRequest;
import com.commerce.backend.model.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AssignUserPermissionServiceImpl implements AssignUserPermissionService{
    public final UserPermissionsRepository userPermissionsRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public AssignUserPermissionServiceImpl(UserPermissionsRepository userPermissionsRepository, RoleRepository roleRepository, UserService userService) {
        this.userPermissionsRepository = userPermissionsRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public BasicResponse getAll(Long roleId) {
        try {
            if(userService.isAdmin()) {
                Role userPermissions = roleRepository.findById(roleId).orElse(null);
                return resHelper.res(userPermissions, true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex){
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse create(AssignUserPermissionRequest assignUserPermission) {
        try {
            if(userService.isAdmin()) {
                if(assignUserPermission.getRoleId() != null || assignUserPermission.getPermissionId() != null) {
                    Role role = roleRepository.findById(assignUserPermission.getRoleId()).orElse(null);
                    UserPermissions userPermissions = userPermissionsRepository.findById(assignUserPermission.getPermissionId()).orElse(null);
                    if(role != null && userPermissions != null){
                        Set<UserPermissions> userPermissions1 =  role.getPermissions();
                        userPermissions1.add(userPermissions);
                        role.setPermissions(userPermissions1);
                        roleRepository.save(role);
                    }
                    return resHelper.res(userPermissions, true, MessageType.Success.getMessage(), null);
                }
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex){
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse remove(AssignUserPermissionRequest assignUserPermission) {
        try {
            if(userService.isAdmin()) {
                if(assignUserPermission.getRoleId() != null || assignUserPermission.getPermissionId() != null) {
                    Role role = roleRepository.findById(assignUserPermission.getRoleId()).orElse(null);
                    UserPermissions userPermissions = userPermissionsRepository.findById(assignUserPermission.getPermissionId()).orElse(null);
                    if(role != null && userPermissions != null){
                        Set<UserPermissions> userPermissions1 =  role.getPermissions();
                        userPermissions1.remove(userPermissions);
                        role.setPermissions(userPermissions1);
                        roleRepository.save(role);
                    }
                    return resHelper.res(userPermissions, true, MessageType.Success.getMessage(), null);
                }
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex){
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }
}
