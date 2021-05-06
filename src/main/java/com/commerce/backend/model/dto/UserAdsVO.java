package com.commerce.backend.model.dto;

import java.util.*;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.converter.KeyResponse;
import com.commerce.backend.model.entity.User;

import com.commerce.backend.model.entity.UserAdsImage;
import lombok.Data;

@Data
public class UserAdsVO {
	
    private long id;

	private String city;

	private String code;
	
	private AdsType type;        
	
	private UserVO createdBy;
	
	private boolean active;
	
	private String name ;
	
	private double longitude;
	
	private double latitude;
	
	private Date created_at;
	
	private Date updated_at;
	
	private String description;
	
	private String short_description;

	private Float price;
	
	private Boolean external_link;
	
	private String categoryName;
	
	private String categoryNameAr;
	
	private Long  categoryId;

	private Set<UserAdsImageVO> Images = new HashSet<UserAdsImageVO>();
  
	private List<Object> extra = new LinkedList<>();
}
