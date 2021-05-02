package com.commerce.backend.dao.userAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.entity.User;
import com.sun.security.auth.UserPrincipal;

import java.util.Collections;
import java.util.List;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        UserPrincipalDetails principal = new UserPrincipalDetails(user);
        return principal;

    }
    
    public UserDetails loadUserByPhone(String mobile) {
        User user = this.userRepository.findByPhone(mobile).orElse(null);
        UserPrincipalDetails principal = new UserPrincipalDetails(user);
        return principal;
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

}