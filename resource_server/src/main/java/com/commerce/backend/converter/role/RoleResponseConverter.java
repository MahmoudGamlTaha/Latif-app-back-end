package com.commerce.backend.converter.role;

import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.response.role.RoleResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoleResponseConverter implements Function<Role, RoleResponse> {
    @Override
    public RoleResponse apply(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setCreated_at(role.getCreated_at());
        return response;
    }
}
