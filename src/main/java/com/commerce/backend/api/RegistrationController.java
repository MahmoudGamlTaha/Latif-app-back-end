package com.commerce.backend.api;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.model.dto.UserDto;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.UserService;
import com.sun.mail.iap.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/registration")
    public ResponseEntity<BasicResponse> registerUserAccount(@RequestBody @Valid UserDto accountDto) throws Exception {
        User user = userService.registerNewUserAccount(accountDto);
        BasicResponse response = new BasicResponse();
        HashMap<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(MessageType.Data.getMessage(), user);
        response.setSuccess(true);
        response.setResponse(mapResponse);
        return new ResponseEntity<BasicResponse>(response, HttpStatus.OK); 
        
    }
}
