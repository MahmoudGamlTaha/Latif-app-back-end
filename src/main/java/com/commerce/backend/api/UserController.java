package com.commerce.backend.api;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.request.user.PasswordResetRequest;
import com.commerce.backend.model.request.user.UpdateUserAddressRequest;
import com.commerce.backend.model.request.user.UpdateUserRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.user.UserResponse;
import com.commerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;


@RestController
public class UserController extends ApiController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/account")
    public ResponseEntity<UserResponse> getUser() {
        UserResponse userResponse = userService.fetchUser();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/account/update")
    public ResponseEntity<BasicResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return new ResponseEntity<>(userService.updateUser(updateUserRequest), HttpStatus.OK);
    }

    @PutMapping(value = "/account/address")
    public ResponseEntity<UserResponse> updateUserAddress(@RequestBody @Valid UpdateUserAddressRequest updateUserAddressRequest) {
        UserResponse userResponse = userService.updateUserAddress(updateUserAddressRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/account/password/reset")
    public ResponseEntity<HttpStatus> passwordReset(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        userService.resetPassword(passwordResetRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/account/status")
    public ResponseEntity<Boolean> getVerificationStatus() {
        Boolean status = userService.getVerificationStatus();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping(value = "/usersList")
    public ResponseEntity<BasicResponse> getUsersList(Long roleId) {
        return new ResponseEntity<>(userService.getUsersList(roleId), HttpStatus.OK);
    }
    @GetMapping(value = "/usersList/all")
    public ResponseEntity<BasicResponse> getUsers(@RequestParam(value = "page") Optional<Integer> page) {
    	Pageable pagable = PageRequest.of(page.orElse(0), SystemConstant.MOBILE_PAGE_SIZE);
        return new ResponseEntity<>(userService.getUsersList(pagable), HttpStatus.OK);
    }
    @PostMapping(value = "/activate-user")
    public ResponseEntity<BasicResponse> activateUser(Long user_id, boolean active){
    	Object sucess = this.userService.activateUser(user_id, active);
     	MessageType message = sucess == null ? MessageType.NotAuthorized:MessageType.Success;
        boolean success = sucess == null? false : true;
        HashMap<String, Object> returnValue = new HashMap<String, Object>();
        returnValue.put("updated", sucess );
    	BasicResponse response = resHelper.res(returnValue, success, message.getMessage(), null);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/account/logout")
    public ResponseEntity<BasicResponse> logout(Long user){
    	 boolean check = this.userService.logout(user);
    	 BasicResponse response  = resHelper.res(check, true, MessageType.Data.getMessage(), null);
    	 return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
}
