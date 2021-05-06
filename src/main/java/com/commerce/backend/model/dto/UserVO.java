package com.commerce.backend.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



import com.commerce.backend.model.entity.SubscriptionTypes;
import com.commerce.backend.model.entity.UserAds;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
	  private Long id;  
	  private Set<UserAds> ads;
	  
	  private String email;

	  private String firstName;

	  private String lastName;

	  private String city;

	  private String state;

	  private String zip;

	 
	  private String phone;

	  private String avatar;

	  private String country;

	  
	  private String address;
	  
	  private Date registrationDate;
	  
	  private int AdsCount;

	  private Set<SubscriptionTypes> subscriptions = new HashSet<>();
}
