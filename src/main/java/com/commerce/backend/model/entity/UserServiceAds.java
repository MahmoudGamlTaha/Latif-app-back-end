package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.commerce.backend.constants.DriverMethods;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;

import lombok.Setter;

@Entity
@DiscriminatorValue(AdsType.Values.SERVICE)
@Getter
@Setter
public class UserServiceAds extends UserAds {
		
	@Column(name = "allow_at_home")
	Boolean allowServiceAtHome;

	@Column(name = "driver_method")
	private DriverMethods driverMethods;
}
