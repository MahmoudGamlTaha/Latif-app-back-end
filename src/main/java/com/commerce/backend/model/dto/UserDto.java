package com.commerce.backend.model.dto;

import com.commerce.backend.validator.PasswordMatches;
import com.commerce.backend.validator.ValidEmail;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class UserDto {
    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    private String mobile;

    @NotNull
    @Size(min = 1)
    private String lastName;

    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    //@ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

    private Long city;

    @NotNull
    private String address;

    @NotNull
    private String avatar;
}

