package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue(AdsType.Values.ACCESSORIES)
@Entity
@Getter
@Setter
public class UserAccAds extends UserAds {
	
	@Column(name = "used")
	private Boolean used;

	@Column(name = "allow_at_home")
	Boolean allowServiceAtHome;

	@Column(name = "stock")
	private int stock;

	@Column(name = "image")
	private String image;

}
