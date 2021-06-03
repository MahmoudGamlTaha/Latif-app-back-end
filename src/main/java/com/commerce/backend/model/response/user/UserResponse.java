package com.commerce.backend.model.response.user;

import java.util.Date;

import lombok.Data;

@Data
public class UserResponse {
	private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String username;
    private String country;
    private Boolean emailVerified;
    private Date    RegistrationDate;
    private String avatar;
}
