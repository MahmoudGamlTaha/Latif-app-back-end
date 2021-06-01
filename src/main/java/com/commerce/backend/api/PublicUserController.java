package com.commerce.backend.api;


import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.user.UserResponseConverter;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.request.user.*;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.user.UserResponse;
import com.commerce.backend.service.TokenService;
import com.commerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import javax.validation.Valid;

@RestController
public class PublicUserController extends PublicApiController {

    private final UserService userService;
    private final TokenService tokenService;
    private final UserResponseConverter userVOConverter;
    @Autowired
    public PublicUserController(UserService userService, TokenService tokenService, UserResponseConverter userVOConverter) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userVOConverter = userVOConverter;
    }

    @PostMapping(value = "/account/registration")
    public ResponseEntity<BasicResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        User user = userService.register(registerUserRequest);
        tokenService.createEmailConfirmToken(user);
        BasicResponse response = new BasicResponse();
        HashMap<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(MessageType.Data.getMessage(), user);
        response.setSuccess(true);
        response.setResponse(mapResponse);
        return new ResponseEntity<BasicResponse>(response, HttpStatus.OK); 
    }

    @PostMapping(value = "/account/registration/validate")
    public ResponseEntity<HttpStatus> validateEmail(@RequestBody @Valid ValidateEmailRequest validateEmailRequest) {
        tokenService.validateEmail(validateEmailRequest.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/forgot")
    public ResponseEntity<HttpStatus> forgotPasswordRequest(@RequestBody @Valid PasswordForgotRequest passwordForgotRequest) {
        tokenService.createPasswordResetToken(passwordForgotRequest.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/forgot/validate")
    public ResponseEntity<HttpStatus> validateForgotPassword(@RequestBody @Valid PasswordForgotValidateRequest passwordForgotValidateRequest) {
        tokenService.validateForgotPassword(passwordForgotValidateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/forgot/confirm")
    public ResponseEntity<HttpStatus> confirmForgotPassword(@RequestBody @Valid PasswordForgotConfirmRequest passwordForgotConfirmRequest) {
        tokenService.validateForgotPasswordConfirm(passwordForgotConfirmRequest.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/account/profile")
    public ResponseEntity<BasicResponse> getCurrentUser(){
    	BasicResponse response = new BasicResponse();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	response.setMsg(MessageType.Success.getMessage());
    	UserResponse userResponse = this.userVOConverter.apply( userService.getCurrentUser());
    	map.put(MessageType.Data.getMessage(), userResponse);
    	response.setResponse(map);
    	return new ResponseEntity<BasicResponse>(response, HttpStatus.OK);
    }
    public ResponseEntity<BasicResponse>getUserList(){
    	BasicResponse response = new BasicResponse();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    //	map.put(MessageType.Data.getMessage(), userService.);
    	return null;
    }
}
