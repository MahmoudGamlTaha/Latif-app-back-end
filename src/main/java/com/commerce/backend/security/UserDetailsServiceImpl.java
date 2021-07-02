package com.commerce.backend.security;

import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    //    try {
            User user = userRepository.findByMobile(s);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with phone: " + s);
            }
            UserDetailsImpl userDetail = new UserDetailsImpl();
            userDetail.setUser(user);
            return userDetail;
      //  } catch (final Exception e) {
        //    throw new RuntimeException(e);
       // }
    }

    public UserDetails loadUserByMobileNum(String mobile) throws UsernameNotFoundException {
        //try {
            User user = userRepository.findByMobile(mobile);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with phone: " + mobile);
            }
            UserDetailsImpl userDetail = new UserDetailsImpl();
            userDetail.setUser(user);
            return userDetail;
       // } catch (final Exception e) {
         //   throw new RuntimeException(e);
       // }
    }
}

