package com.commerce.backend.api;

import com.commerce.backend.model.entity.UserRole;
import com.commerce.backend.model.request.role.UserRoleRequest;
import com.commerce.backend.model.request.role.UserRoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;
import com.commerce.backend.service.UserRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRoleController extends PublicApiController {

    private final UserRoleServiceImpl service;

    @Autowired
    public UserRoleController(UserRoleServiceImpl service) {
        this.service = service;
    }

    @GetMapping("userRole/{userId}")
    public RoleResponse getUserRole(@PathVariable Long userId) throws Exception {
        return service.getUserRoleByUserId(userId);
    }

    @PostMapping("/userRole/create")
    public BasicResponse createUserRole(UserRoleRequest userRole) throws Exception {
        return service.create(userRole);
    }

    @PostMapping("/userRole/update")
    public UserRole updateUserRole(UserRoleRequestUpdate userRole) throws Exception {
        return service.update(userRole);
    }

    @DeleteMapping("/userRole/delete")
    public BasicResponse deleteUserRole(Long id) throws Exception {
        return service.delete(id);
    }
}
