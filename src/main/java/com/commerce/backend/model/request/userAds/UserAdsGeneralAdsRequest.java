package com.commerce.backend.model.request.userAds;

import javax.validation.constraints.NotBlank;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.UserAds;

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
     private UserMedicalAdsRequest userMedicalAdsRequest;
     public UserAdsRequest getUserAds() {
    	 if(type == AdsType.ACCESORIESS) {
    		 return this.getUserAccRequest();
    	 }
    	 else if(type == AdsType.PET_CARE) {
    		 return this.getUserMedicalAdsRequest();
    	 }
    	 else if(type == AdsType.PETS) {
    		 return this.getUserPetsAdsRequest();
    	 }
    	 else if(type == AdsType.SERVICE) {
    		 return this.getUserServiceAdsRequest();
    	 }
    	 return null;
     }
}
