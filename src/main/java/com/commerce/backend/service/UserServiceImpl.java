package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.user.UserResponseConverter;
import com.commerce.backend.dao.ItemObjectCategoryRepository;
import com.commerce.backend.dao.RoleRepository;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.dto.UserDto;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.request.user.PasswordResetRequest;
import com.commerce.backend.model.request.user.RegisterUserRequest;
import com.commerce.backend.model.request.user.UpdateUserAddressRequest;
import com.commerce.backend.model.request.user.UpdateUserRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;
import com.commerce.backend.model.response.user.UserResponse;
import com.commerce.backend.security.UserDetailsImpl;
import com.google.firebase.messaging.FirebaseMessaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.commerce.backend.converter.category.ItemObjectCategoryResponseConverter;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserResponseConverter userResponseConverter;
    private final ItemObjectCategoryRepository itemObjectCategoryRepository;
    private final ItemObjectCategoryResponseConverter ItemObjectCategoryResponseConverter;
    private RoleRepository roleRepository;
    
    @Autowired
    private FirebaseMessaging firebaseMessage;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserResponseConverter userResponseConverter, ItemObjectCategoryRepository itemObjectCategoryRepository, ItemObjectCategoryResponseConverter itemObjectCategoryResponseConverter, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResponseConverter = userResponseConverter;
        this.itemObjectCategoryRepository = itemObjectCategoryRepository;
        ItemObjectCategoryResponseConverter = itemObjectCategoryResponseConverter;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(RegisterUserRequest registerUserRequest) {
        if (userExists(registerUserRequest.getEmail(), registerUserRequest.getPhone())) {
            throw new InvalidArgumentException("An account already exists");
        }
        

        User user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setEmailVerified(false);
        user.setMobile(registerUserRequest.getPhone());
        user.setActive(true);
        user.setRegistrationDate(new Date());
        String[] names = registerUserRequest.getName().split(" ");
   
        user.setFirstName(names[0]);
        if(names.length > 1) {
        user.setLastName(names[1]);
        }
        return userRepository.save(user);
    }

    @Override
    public UserResponse fetchUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("Invalid access");
        }

        Optional<User> user = userRepository.findByEmail(userName);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return userResponseConverter.apply(user.get());
    }


    @Override
    public User getUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("Invalid access");
        }

        Optional<User> user = userRepository.findByEmail(userName);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public User saveUser(User user) {
        if (Objects.isNull(user)) {
            throw new InvalidArgumentException("Null user");
        }

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new InvalidArgumentException("Null email");
        }

        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean userExists(String email, String mobile) {
        return userRepository.existsByEmail(email) || userRepository.findByMobile(mobile) != null;
    }

    @Override
    public BasicResponse updateUser(UpdateUserRequest updateUserRequest) {
        if(updateUserRequest.getId() == null){
            return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
        }
        User user = userRepository.findById(updateUserRequest.getId()).orElse(null);
        if(user != null){
            if(isAdmin() || isAuthorized(user)) {
                if(updateUserRequest.getFirstName() != null) {
                    user.setFirstName(updateUserRequest.getFirstName());
                }
                if(updateUserRequest.getLastName() != null) {
                    user.setLastName(updateUserRequest.getLastName());
                }
                //if(updateUserRequest.getPhone() != null) {
                //    user.setMobile(updateUserRequest.getPhone());
                //}
                if(updateUserRequest.getEmail() != null) {
                    user.setEmail(updateUserRequest.getEmail());
                }
                if(updateUserRequest.getCity() != null) {
                    user.setCity(updateUserRequest.getCity());
                }
                if(updateUserRequest.getCountry() != null) {
                    user.setCountry(updateUserRequest.getCountry());
                }
                if(updateUserRequest.getState() != null) {
                    user.setState(updateUserRequest.getState());
                }
                if(updateUserRequest.getAddress() != null) {
                    user.setAddress(updateUserRequest.getAddress());
                }
                if(updateUserRequest.getUsername() != null) {
                    user.setUsername(updateUserRequest.getUsername());
                }
                user = userRepository.save(user);
                return resHelper.res(userResponseConverter.apply(user), true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }else{
            return resHelper.res(null, false, MessageType.NotFound.getMessage(), null);
        }
    }

    @Override
    public UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest) {
        User user = getUser();
        user.setAddress(updateUserAddressRequest.getAddress());
        user.setCity(updateUserAddressRequest.getCity());
        user.setState(updateUserAddressRequest.getState());
        user.setZip(updateUserAddressRequest.getZip());
        user.setCountry(updateUserAddressRequest.getCountry());

        user = userRepository.save(user);
        return userResponseConverter.apply(user);
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        User user = getUser();
        if (!passwordEncoder.matches(passwordResetRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidArgumentException("Invalid password");
        }

        if (passwordEncoder.matches(passwordResetRequest.getNewPassword(), user.getPassword())) {
            return;
        }

        user.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public Boolean getVerificationStatus() {
        User user = getUser();
        return user.getEmailVerified() == true;
    }

    @Override
    public User findUserByMobileNumber(String mobile) {
        if(mobile != null){
            return userRepository.findByMobile(mobile);
        }
        return null;
    }

    @Override
    public User registerNewUserAccount(UserDto accountDto) throws Exception {
        if (mobileExists(accountDto.getMobile())) {
            throw new Exception("There is an account with that mobile number: " + accountDto.getMobile());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setMobile(accountDto.getMobile());
        user.setUsername(accountDto.getUsername());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRegistrationDate(new Date());
        user.setCity(accountDto.getCity());
        user.setCountry(accountDto.getCountry());
        user.setState(accountDto.getState());
        user.setAddress(accountDto.getAddress());
        user.setRoles(roleRepository.findByName("USER"));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {

    }

    public boolean mobileExists(String m){
        return userRepository.findByMobile(m) != null;
    }

    @Override
    public User getCurrentUser(){
        String principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return this.findUserByMobileNumber(principle);
    }

    @Override
    public boolean isAdmin(){
        User user = getCurrentUser();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    /**
     *
     * @param user
     * @return
     *
     * check if user is the owner and has authority to make change
     */

    @Override
    public boolean isAuthorized(User user) {
        return getCurrentUser() == user;
    }

    @Override
    public BasicResponse getUsersList(Long roleId) {
        if(roleId != null && isAdmin()) {
            Role role = roleRepository.findById(roleId).orElse(null);
            if(role != null) {
                List<User> users = userRepository.findUsersByRoles(role);
                List<UserResponse> userVO = new ArrayList<>();
                users.forEach(user -> userVO.add(userResponseConverter.apply(user)));
                return resHelper.res(userVO, true, MessageType.Success.getMessage(), null);
            }
            return resHelper.res(null, false, MessageType.NotFound.getMessage(), null);
        }
        return resHelper.res(null, false, MessageType.Fail.getMessage(), null);
    }

    @Override
    public BasicResponse getUserInterestCategories() {
        User user = getCurrentUser();
        if(user != null) {
            return resHelper.res(user.getInterestCategories(), true, MessageType.Success.getMessage(), null);
        }
        return resHelper.res(null, true, MessageType.NotAuthorized.getMessage(), null);
    }

    @Override
    public BasicResponse createInterestCategories(List<Long> categories) {
        User user = getCurrentUser();
        if(user != null) {
        	categories.forEach(categoryId -> {
            ItemObjectCategory itemObjectCategory = itemObjectCategoryRepository.findById(categoryId).orElse(null);
            if(itemObjectCategory != null) {
                Set<ItemObjectCategory> itemObjectCategorySet = user.getInterestCategories();
                boolean exists = itemObjectCategorySet.contains(itemObjectCategory);
                if(!exists) {
                    itemObjectCategorySet.add(itemObjectCategory);
                    user.setInterestCategories(itemObjectCategorySet);
                   
                }}});
        	        userRepository.save(user);
                    List<ItemObjectCategoryResponse> itemObjectCategoryVOS = new ArrayList<>();
                    user.getInterestCategories().forEach(category -> 
                    itemObjectCategoryVOS.add(ItemObjectCategoryResponseConverter.apply(category)));
                    return resHelper.res(itemObjectCategoryVOS, true, MessageType.Success.getMessage(), null);
                
       }
        return resHelper.res(null, true, MessageType.NotAuthorized.getMessage(), null);
    }

    @Override
    public BasicResponse removeInterestCategories(Long categoryId) {
        User user = getCurrentUser();
        if(user != null) {
            ItemObjectCategory itemObjectCategory = itemObjectCategoryRepository.findById(categoryId).orElse(null);
            if(itemObjectCategory != null) {
                Set<ItemObjectCategory> itemObjectCategorySet = user.getInterestCategories();
                boolean exists = itemObjectCategorySet.contains(itemObjectCategory);
                if(exists) {
                    itemObjectCategorySet.remove(itemObjectCategory);
                    user.setInterestCategories(itemObjectCategorySet);
                    userRepository.save(user);
                    List<ItemObjectCategoryResponse> itemObjectCategoryVOS = new ArrayList<>();
                    user.getInterestCategories().forEach(category -> itemObjectCategoryVOS.add(ItemObjectCategoryResponseConverter.apply(category)));
                    return resHelper.res(itemObjectCategoryVOS, true, MessageType.Success.getMessage(), null);
                }
            }
            return resHelper.res(null, true, MessageType.NotFound.getMessage(), null);
        }
        return resHelper.res(null, true, MessageType.NotAuthorized.getMessage(), null);
    }

    @Override
    public BasicResponse getUsersInterestCategories() {
        if(isAdmin()) {
            List<ItemObjectCategory> itemObjectCategory = itemObjectCategoryRepository.findAll();
            HashMap<Long, List<UserResponse>> UsersList = new HashMap<>();
            itemObjectCategory.forEach(category -> {
                if(category.getUsers().size() != 0) {
                    List<UserResponse> tt = new ArrayList<>();
                    category.getUsers().forEach(user -> tt.add(userResponseConverter.apply(user)));
                    UsersList.put(category.getId(), tt);
                }
            });
            return resHelper.res(UsersList, true, MessageType.Success.getMessage(), null);
        }
        return resHelper.res(null, true, MessageType.NotAuthorized.getMessage(), null);
    }

	@Override
	public Object activateUser(Long userId, boolean active) {
		Object sucess = null;
		if(isAdmin()) {
			sucess = this.userRepository.activateUser(userId, active);	
           sucess = true; 
		}
		return sucess;
	}
	
	  @Override
	    public BasicResponse getUsersList(Pageable pageable) {
	        if( isAdmin()) {
	                Page<User> users = userRepository.findAll(pageable);
	                List<UserResponse> userVO = new ArrayList<>();
	                users.forEach(user -> userVO.add(userResponseConverter.apply(user)));
	                return resHelper.res(userVO, true, MessageType.Success.getMessage(), pageable);
	          
	        }
	        return resHelper.res(null, false, MessageType.Fail.getMessage(), pageable);
	    }
}
