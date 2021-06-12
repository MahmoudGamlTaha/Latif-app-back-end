package com.commerce.backend.service;


import org.springframework.data.domain.Pageable;

import com.commerce.backend.model.dto.UserDto;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.request.user.PasswordResetRequest;
import com.commerce.backend.model.request.user.RegisterUserRequest;
import com.commerce.backend.model.request.user.UpdateUserAddressRequest;
import com.commerce.backend.model.request.user.UpdateUserRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.user.UserResponse;

public interface UserService {
    User register(RegisterUserRequest registerUserRequest);

    UserResponse fetchUser();

    User getUser();

    User saveUser(User user);

    User findByEmail(String email);

    boolean userExists(String email);

    BasicResponse updateUser(UpdateUserRequest updateUserRequest);

    UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest);

    void resetPassword(PasswordResetRequest passwordResetRequest);

    Boolean getVerificationStatus();

    User findUserByMobileNumber(String mobile);

    User registerNewUserAccount(UserDto accountDto) throws Exception;

    void deleteUser(User user);

    boolean isAdmin();

    User getCurrentUser();

    boolean isAuthorized(User user);

    BasicResponse getUsersList(Long roleId);
   
    BasicResponse getUsersList(Pageable pageable);
    
    BasicResponse getUserInterestCategories();

    BasicResponse createInterestCategories(Long categoryId);

    BasicResponse removeInterestCategories(Long categoryId);

    BasicResponse getUsersInterestCategories();
    
    Object activateUser(Long userId, boolean activate);
}
