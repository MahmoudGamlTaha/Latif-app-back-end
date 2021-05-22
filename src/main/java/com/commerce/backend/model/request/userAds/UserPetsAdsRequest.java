package com.commerce.backend.model.request.userAds;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.commerce.backend.constants.FoodType;
import com.commerce.backend.constants.TrainningType;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserPetsAdsRequest extends UserAdsRequest {
	private MultipartFile image;
	
	private Float price;
	
	private String breed;
	
	private Integer stock;
	
	private Boolean weaned;
	
	private Boolean neutering;
	
	private Boolean vaccinationCertificate;
	
	private Boolean training;
	
	private Boolean barkingProblem;
	
	private Boolean passport;
	
	private Boolean playWithKids;
	
	private Boolean diseasesDisabilities;
	
	private String diseasesDisabilitiesDesc;
	
	private Long category;
	
	private Long categoryType;
	
	private Integer food;
	
	private String selling_type;
	
	private Set<MultipartFile> petsImages;
}
