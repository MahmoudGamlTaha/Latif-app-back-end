package com.commerce.backend.model.response.role;

import com.commerce.backend.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleResponse {
    private Long id;
    private String name;
    private Date created_at;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.created_at = role.getCreated_at();
    }
}
