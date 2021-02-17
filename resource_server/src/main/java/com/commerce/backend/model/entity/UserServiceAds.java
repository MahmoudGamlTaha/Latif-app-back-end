package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.commerce.backend.constants.AdsType;

@Entity
@DiscriminatorValue(AdsType.Values.SERVICE)
public class UserServiceAds extends UserAds {
	@OneToOne
	@JoinColumn(name ="category_id")
	ServiceCategory serviceCategory;
	
}
