package com.commerce.backend.service.cache;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.dao.UserRoleRepository;
import com.commerce.backend.model.composite.UserRoleKey;
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

@Service
public class UserRoleCacheServiceImpl implements UserRoleCacheService{

    private final UserRoleRepository repo;
    private final RoleServiceImpl roleService;

    @Autowired
    public UserRoleCacheServiceImpl(UserRoleRepository repo, RoleServiceImpl roleService) {
        this.repo = repo;
        this.roleService = roleService;
    }

    @Override
    public UserRole create(UserRoleRequest userRole) throws Exception {
        try {
        	UserRoleKey key = new UserRoleKey();
        	key.setRoleId(userRole.getRoleId());
        	key.setUserId(userRole.getUserId());
        	User user = new User();
         
        	user.setId(userRole.getUserId());
            Role role = new Role();
            role.setId(userRole.getRoleId());
            UserRole ur = UserRole.builder()
                    .user(user)
                    .role(role)
                    .created_at(new Date())
                    .build();
            return repo.save(ur);
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
            UserRoleKey key = new UserRoleKey();
            userRole.setId(userRole.getId());
            
            if(userRole.getRoleId() != null)
            {
		        
                ur.getRole().setId(userRole.getRoleId());
            }

            if(userRole.getUserId() != null)
            {
                ur.getUser().setId(userRole.getUserId());
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
            Long roleId = repo.getUserRoleByUserId(id).getRole().getId();
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
            response.setMsg(MessageType.Fail.getMessage());
        }
        return response;
    }
}
