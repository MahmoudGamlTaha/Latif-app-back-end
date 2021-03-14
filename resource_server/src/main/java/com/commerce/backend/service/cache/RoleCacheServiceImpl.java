package com.commerce.backend.service.cache;

import com.commerce.backend.dao.RoleRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.request.role.RoleRequest;
import com.commerce.backend.model.request.role.RoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "role")
public class RoleCacheServiceImpl implements RoleCacheService{

    private final RoleRepository repo;

    @Autowired
    public RoleCacheServiceImpl(RoleRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<Role> getRolesList()
    {
        return repo.findAll();
    }

    @Override
    @Cacheable(key = "#id", unless = "{#root.caches[0].get(#id) == null, #result.equals(null)}")
    public RoleResponse getRoleById(Long id) {
        Role Role = repo.findById(id).orElse(null);
        if(Objects.isNull(Role))
        {
            throw new ResourceNotFoundException("Not Found");
        }
        return new RoleResponse(Role);
    }

    @Override
    public Role createRole(RoleRequest role) throws Exception {
        Role Role = new Role();
        if(role.getName() == null){
            throw new Exception("please provide a name");
        }
        Role.setName(role.getName());
        Role.setCreated_at(new Date());
        repo.save(Role);
        return Role;
    }

    @Override
    public Role update(RoleRequestUpdate role) throws Exception {
        if(role.getId() != null)
        {
            Role Role = repo.findById(role.getId()).orElse(null);
            if(Role == null)
            {
                throw new Exception("Not Found");
            }
            if(role.getName() != null){
                Role.setName(role.getName());
                Role.setUpdated_at(new Date());
                repo.save(Role);
            }
            return Role;
        }else
        {
            throw new Exception("Not Found");
        }
    }

    @Override
    public BasicResponse delete(Long id) {
        BasicResponse response = new BasicResponse();
        try{
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
