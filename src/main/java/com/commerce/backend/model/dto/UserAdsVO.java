package com.commerce.backend.model.dto;

import java.util.*;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.converter.KeyResponse;
import com.commerce.backend.model.entity.User;

import lombok.Data;

@Data
public class UserAdsVO {
	
    private Object id;
	
	private Object code;

	private Object city;

	private Object type;

	private String code;
	
	private AdsType type;        
	
	private UserVO createdBy;
	
	private boolean active;
	
	private Object active;
	
	private Object name ;
	
	private Object longitude;
	
	private Object latitude;
	
	private Object created_at;
	
	private Object updated_at;
	
	private Object description;
	
	private Object short_description;

	private Object price;
	
	private Object external_link;
	
	private Object categoryName;
	
	private Object categoryNameAr;
	
	private Object  categoryId;
  
	private List<Object> extra = new LinkedList<>();
}
