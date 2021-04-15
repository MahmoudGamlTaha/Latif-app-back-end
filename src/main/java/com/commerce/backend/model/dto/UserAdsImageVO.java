package com.commerce.backend.model.dto;

import lombok.Data;

@Data
public class UserAdsImageVO {
	private long id;
	private String image;
	private Boolean external_link;
	private long userAdsId;
}
