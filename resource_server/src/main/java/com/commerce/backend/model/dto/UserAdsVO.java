package com.commerce.backend.model.dto;

import java.util.Date;


import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.User;

import lombok.Data;

@Data
public class UserAdsVO {
	
    private long id;
	
	
	private String code;
	
	private AdsType type;        
	
	private User createdBy;
	
	private boolean active;
	
	private String name ;
	
	private String longitude;
	
	private String latitude;
	
	private Date created_at;
	
	private Date updated_at;
	
	private String description;
	
	private String short_description;
}
