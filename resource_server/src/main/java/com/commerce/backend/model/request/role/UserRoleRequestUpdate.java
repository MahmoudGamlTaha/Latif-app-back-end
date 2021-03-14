package com.commerce.backend.model.request.role;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class UserRoleRequestUpdate {

    @NotNull
    @Positive
    Long id;

    @NotNull
    @Positive
    Long userId;

    @NotNull
    @Positive
    Long roleId;
}
