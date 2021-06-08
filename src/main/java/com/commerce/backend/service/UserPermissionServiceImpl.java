package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.dao.UserPermissionsRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.entity.UserPermissions;
import com.commerce.backend.model.request.userPermission.UpdateUserPermissionRequest;
import com.commerce.backend.model.request.userPermission.UserPermissionRequest;
import com.commerce.backend.model.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserPermissionServiceImpl implements UserPermissionService{
    public final UserPermissionsRepository userPermissionsRepository;
    private final UserService userService;

    @Autowired
    public UserPermissionServiceImpl(UserPermissionsRepository userPermissionsRepository, UserService userService) {
        this.userPermissionsRepository = userPermissionsRepository;
        this.userService = userService;
    }

    @Override
    public BasicResponse getAll(Integer page, Integer pageSize) {
        try {
            if(userService.isAdmin()) {
                Pageable pageable = PageRequest.of(page, pageSize);
                Page<UserPermissions> userPermissions = userPermissionsRepository.findAll(pageable);
                return resHelper.res(userPermissions, true, MessageType.Success.getMessage(), pageable);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex){
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse create(List<UserPermissionRequest> userPermissionRequest) {
        try {
            if(userService.isAdmin()) {
                List<Object> userPermissions = new ArrayList<>();
                userPermissionRequest.forEach( u ->
                        userPermissions.add(save(u, userPermissionRequest.indexOf(u)))
                );
                return resHelper.res(userPermissions, true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex){
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }

    public Object save(UserPermissionRequest userPermissionRequest, Integer index){
        if(userPermissionRequest.getHttpPath() != null && userPermissionRequest.getHttpMethod() != null) {
            boolean userPermissionExists = userPermissionsRepository.existsUserPermissionsByHttpPath(userPermissionRequest.getHttpPath());
            if(!userPermissionExists) {
                UserPermissions userPermissions = new UserPermissions();
                userPermissions.setHttpMethod(userPermissionRequest.getHttpMethod().toUpperCase());
                userPermissions.setHttpPath(userPermissionRequest.getHttpPath()+"*//**");
                userPermissions.setName(UUID.randomUUID().toString());
                userPermissions.setCreated_at(new Date());
                userPermissionsRepository.save(userPermissions);
                return userPermissions;
            }else {
                return "http path [" + userPermissionRequest.getHttpPath()+ "] already exists in index :" + index;
            }
        }else{
            return MessageType.NullValue.getMessage() + " in index: " + index;
        }
    }

    @Override
    public BasicResponse update(List<UpdateUserPermissionRequest> userPermissionRequest) {
        try {
            if(userService.isAdmin()) {
                List<Object> userPermissions = new ArrayList<>();
                userPermissionRequest.forEach( u ->
                        userPermissions.add(update(u, userPermissionRequest.indexOf(u)))
                );
                return resHelper.res(userPermissions, true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex){
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }

    public Object update(UpdateUserPermissionRequest userPermissionRequest, Integer index){
        if(userPermissionRequest.getId() != null) {
            UserPermissions userPermissions = userPermissionsRepository.findById(userPermissionRequest.getId()).orElse(null);
            if(userPermissions != null) {
                if(userPermissionRequest.getHttpMethod() != null) {
                    userPermissions.setHttpMethod(userPermissionRequest.getHttpMethod());
                }
                if(userPermissionRequest.getHttpPath() != null) {
                    userPermissions.setHttpPath(userPermissionRequest.getHttpPath());
                }
                userPermissions.setUpdated_at(new Date());
                userPermissionsRepository.save(userPermissions);
                return userPermissions;
            }else {
                return MessageType.NotFound.getMessage()+" in index :" + index;
            }
        }else{
            return MessageType.NullValue.getMessage() + " in index: " + index;
        }
    }

    @Override
    public BasicResponse remove(Long id) {
        try {
            if(userService.isAdmin()) {
                if(id != null) {
                    UserPermissions userPermissions = userPermissionsRepository.findById(id).orElse(null);
                    if(userPermissions != null){
                        userPermissionsRepository.delete(userPermissions);
                    }
                    return resHelper.res(null, true, MessageType.NotFound.getMessage(), null);
                }
                return resHelper.res("deleted successfully!", true, MessageType.Deleted.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex){
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }
}
