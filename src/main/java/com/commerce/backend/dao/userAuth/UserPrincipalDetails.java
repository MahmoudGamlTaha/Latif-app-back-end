package com.commerce.backend.dao.userAuth;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserRole;

public class UserPrincipalDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	public UserPrincipalDetails(User user) {
		this.user = user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authority = new LinkedList<GrantedAuthority>();
		 Set<UserRole> roles= this.user.getRoles();
		 if(roles != null) {
			 roles.forEach(role -> {
				 GrantedAuthority auth = new SimpleGrantedAuthority(role.getRole().getName());
				 authority.add(auth);
			 });
		 }
		return authority;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
    public String getPhoneNumber() {
    	return this.getPhoneNumber();
    }
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.user.getActive();
	}

}
