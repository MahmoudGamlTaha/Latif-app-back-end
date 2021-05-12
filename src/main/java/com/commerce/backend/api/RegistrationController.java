package com.commerce.backend.api;

import com.commerce.backend.model.dto.UserDto;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/registration")
    public User registerUserAccount(@RequestBody @Valid UserDto accountDto) throws Exception {
        return userService.registerNewUserAccount(accountDto);
    }
}
