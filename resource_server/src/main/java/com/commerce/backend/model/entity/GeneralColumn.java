package com.commerce.backend.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralColumn {
	
	@Column(name = "created_at")
	private Date created_at;
	
	@Column(name = "updated_at")
	private Date updated_at;
}
