package com.commerce.backend.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue(AdsType.Values.PETS)
public class UserPetAds extends UserAds {
    
	@Column(name = "image")
	private String image;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "breed")
	private String breed;
	
	@Column(name = "stock")
	private Integer stock;
	
	@Column(name = "weaned")
	private Boolean weaned;
	
	@Column(name = "neutering")
	private Boolean neutering;
	
	@Column(name = "vaccination_certifcate")
	private Boolean vaccinationCertifcate;
	
	@Column(name = "trainning")
	private Boolean trainning;
	
	@Column(name = "barking_problem")
	private Boolean barkingProblem;
	
	@Column(name = "passport")
	private Boolean passport;
	
	@Column(name = "play_with_kids")
	private Boolean playWithKids;
	
	@Column(name = "diseases_disabilities")
	private Boolean diseasesDisabilities;
	
	
	@OneToOne
	@JoinColumn(name ="category_id")
	private PetCategory category;
	
	@OneToMany(mappedBy="userAdsImage", cascade = CascadeType.ALL)
	private Set<UserAdsImage> petsImages;
	
}
