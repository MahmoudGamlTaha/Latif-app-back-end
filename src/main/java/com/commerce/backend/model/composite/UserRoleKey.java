package com.commerce.backend.model.composite;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserRoleKey {
    
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "user_id")
	private Long userId;
}
