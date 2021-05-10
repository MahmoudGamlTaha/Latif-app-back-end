package com.commerce.backend.security;

import com.commerce.backend.model.entity.Role;
import com.commerce.backend.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {
    private User user;

    public UserDetailsImpl(User user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();

        Set<Role> _roles = null;

        if (user!=null) {
            _roles = user.getRoles();
        }

        if (_roles!=null) {
            for (Role _role : _roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+_role.getName());
                authorities.add(authority);
                _role.getPermissions().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getName())));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getMobileNum() {
        return user.getMobile();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}

