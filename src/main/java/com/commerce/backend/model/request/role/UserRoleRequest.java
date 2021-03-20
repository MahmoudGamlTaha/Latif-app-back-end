package com.commerce.backend.model.request.role;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class UserRoleRequest {

    @NotNull
    @Positive
    Long userId;

    @NotNull
    @Positive
    Long roleId;
}
