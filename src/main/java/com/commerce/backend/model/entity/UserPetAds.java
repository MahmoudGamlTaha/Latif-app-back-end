package com.commerce.backend.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.FoodType;
import com.commerce.backend.constants.TrainningType;

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
	
	@Column(name = "breed")
	private String breed;
	
	@Column(name = "stock")
	private Integer stock;
	
	@Column(name = "weaned")
	private Boolean weaned;
	
	@Column(name = "neutering")
	private Boolean neutering;
	
	@Column(name = "vaccination_certificate")

	private Boolean vaccinationCertifcate;
	
	@Column(name = "training")
	@Enumerated(EnumType.STRING)
	private TrainningType training;
	
	@Column(name = "food")
	private String food;
	
	@Column(name = "barking_problem")
	private Boolean barkingProblem;
	
	@Column(name = "passport")
	private Boolean passport;
	
	@Column(name = "play_with_kids")
	private Boolean playWithKids;
	
	@Column(name = "diseases_disabilities")
	private Boolean diseasesDisabilities;
	
	@Column(name = "diseases_disabilities_desc")
	private String diseasesDisabilitiesDesc;

	//@ManyToOne
	//@JoinColumn(name ="category_type")
	//private PetCategory petCategoryType;

	//@ManyToOne
	//@JoinColumn(name ="category_id")
	//private PetCategory category;
	
}
