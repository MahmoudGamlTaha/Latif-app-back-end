package com.commerce.backend.model.request.user;

import com.commerce.backend.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {

    @NotNull
    private Long id ;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @Size(min = 3, max = 26)
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @Size(min = 3, max = 26)
    private String lastName;

    //@Pattern(regexp = "[0-9]+")
    //@Size(min = 11, max = 12)
    //private String phone;

    //@ValidEmail
    @Size(min = 1)
    private String email;

    @Size(min = 1)
    private String username;

    private String city;

    private String state;

    private String country;

    private String address;

    private String avatar;
}
