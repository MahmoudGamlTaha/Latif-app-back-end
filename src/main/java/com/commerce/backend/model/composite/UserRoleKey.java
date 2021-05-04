package com.commerce.backend.model.composite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserRoleKey implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "user_id")
	private Long userId;
}
