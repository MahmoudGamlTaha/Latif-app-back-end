package com.commerce.backend.model.request.userAds;

import javax.validation.constraints.NotBlank;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdsGeneralAdsRequest {
	@NotBlank
     private AdsType type;
     
	 private UserPetsAdsRequest userPetsAdsRequest;
     private UserServiceAdsRequest userServiceAdsRequest;
     private UserAccAdsRequest userAccRequest;
}
