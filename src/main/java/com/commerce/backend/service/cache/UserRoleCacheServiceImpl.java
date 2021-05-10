package com.commerce.backend.service.cache;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.dao.RoleRepository;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.dao.UserRoleRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserRole;
import com.commerce.backend.model.request.role.UserRoleRequest;
import com.commerce.backend.model.request.role.UserRoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;
import com.commerce.backend.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserRoleCacheServiceImpl implements UserRoleCacheService{

    private final UserRoleRepository repo;
    private final RoleServiceImpl roleService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserRoleCacheServiceImpl(UserRoleRepository repo, RoleServiceImpl roleService, UserRepository userRepository, RoleRepository roleRepository) {
        this.repo = repo;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public BasicResponse create(UserRoleRequest userRole) throws Exception {
        try {
            User user = userRepository.findById(userRole.getUserId()).orElse(null);
            if(user == null){
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }
            Role role = roleRepository.findById(userRole.getRoleId()).orElse(null);
            if(role == null){
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }
            UserRole userRole1 = repo.getUserRoleByUserId(userRole.getUserId());
            if(userRole1.getRoleId().equals(userRole.getRoleId())) {
                return resHelper.res(null, false, MessageType.Fail.getMessage(), null);
            }
            Set<Role> test = new HashSet<>();
            test.add(role);
            user.setRoles(test);
            return resHelper.res(userRepository.save(user), true, "success", null);
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    @Override
    public UserRole update(UserRoleRequestUpdate userRole) throws Exception {
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
            ur.setUpdated_at(new Date());
            return repo.save(ur);
        }catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    @Override
    public RoleResponse getUserRoleByUserId(Long id) throws Exception {
        try {
            Long roleId = repo.getUserRoleByUserId(id).getRoleId();
            return roleService.getRoleById(roleId);
        }catch (Exception e)
        {
            throw new Exception("user role not found "+e);
        }
    }

    @Override
    public BasicResponse delete(Long id) throws Exception {
        BasicResponse response = new BasicResponse();
        try {
            assert id != null;
            repo.deleteById(id);
            response.setSuccess(true);
            response.setMsg("Removed");
        }catch (Exception e)
        {
            response.setSuccess(false);
            response.setMsg("Error");
        }
        return response;
    }
}
