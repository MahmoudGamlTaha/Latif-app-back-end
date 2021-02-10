package com.commerce.backend.model.request.userAds;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdsRequest {
	private AdsType type;        
		
	private long createdBy;
	
	private boolean active;
	 
	@NotBlank
	 @Size(min = 3, max = 80)
	 @Pattern(regexp = "[0-9a-zA-Z #,-,_]+")
	private String name ;
	
	 @NotBlank
	 @Pattern(regexp = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$\r\n")
	private String longitude;

	 @NotBlank
	 @Pattern(regexp= "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$\r\n")
	private String latitude;
	
	 @Pattern(regexp = "[0-9a-zA-Z #,-,_]+")
	private String description;
	 
	 @NotBlank
	 @Size(min = 10, max= 200)
	 @Pattern(regexp = "[0-9a-zA-Z #,-,_]+")
	private String short_description;
}