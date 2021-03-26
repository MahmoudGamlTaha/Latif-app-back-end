package com.commerce.backend.model.request.userAds;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccAdsRequest extends UserAdsRequest {
 
	private Boolean used; 
	private Long category_type_id;
	private Long category_id;
}
