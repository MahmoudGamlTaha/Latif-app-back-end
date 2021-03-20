package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	ServiceCategory serviceCategory;
	
	@Column(name = "allow_at_home")
	Boolean allowServiceAtHome;
}
