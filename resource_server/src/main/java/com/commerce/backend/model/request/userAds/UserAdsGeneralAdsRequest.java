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
     private UserAdsRequest userAdsRequest;
     public UserAdsRequest getUserAds() {
    	 if(type == AdsType.ACCESSORIES) {
    	 	this.userAdsRequest = new UserAccAdsRequest();
    	 }
    	 else if(type == AdsType.PET_CARE) {
			 this.userAdsRequest = new UserMedicalAdsRequest();
		 }
    	 else if(type == AdsType.PETS) {
			 this.userAdsRequest = new UserPetsAdsRequest();
    	 }
    	 else if(type == AdsType.SERVICE) {
    		this.userAdsRequest = new UserServiceAdsRequest();
    	 }
    	 return this.userAdsRequest;
     }
}
