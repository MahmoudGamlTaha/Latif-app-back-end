package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(AdsType.Values.SERVICE)
@Getter
@Setter
public class UserServiceAds extends UserAds {
	@ManyToOne
	@JoinColumn(name ="category_type")
	ServiceCategory serviceCategoryType;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	ServiceCategory serviceCategory;
	
}
