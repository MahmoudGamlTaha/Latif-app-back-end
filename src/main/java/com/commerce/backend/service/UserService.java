package com.commerce.backend.service;


import com.commerce.backend.model.dto.UserDto;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.request.user.PasswordResetRequest;
import com.commerce.backend.model.request.user.RegisterUserRequest;
import com.commerce.backend.model.request.user.UpdateUserAddressRequest;
import com.commerce.backend.model.request.user.UpdateUserRequest;
import com.commerce.backend.model.response.user.UserResponse;

public interface UserService {
    User register(RegisterUserRequest registerUserRequest);

    UserResponse fetchUser();

    User getUser();

    User saveUser(User user);

    User findByEmail(String email);

    boolean userExists(String email);

    UserResponse updateUser(UpdateUserRequest updateUserRequest);

    UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest);

    void resetPassword(PasswordResetRequest passwordResetRequest);

    Boolean getVerificationStatus();

    User findUserByMobileNumber(String mobile);

    User registerNewUserAccount(UserDto accountDto) throws Exception;

    void deleteUser(User user);

    boolean isAdmin();

    User getCurrentUser();

    boolean isAuthorized(User user);
}
