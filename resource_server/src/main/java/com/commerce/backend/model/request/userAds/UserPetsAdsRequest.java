package com.commerce.backend.model.request.userAds;

import java.io.File;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserPetsAdsRequest extends UserAdsRequest {
	private File image;
	
	private Float price;
	
	private String breed;
	
	private Integer stock;
	
	private Boolean weaned;
	
	private Boolean neutering;
	
	private Boolean vaccinationCertifcate;
	
	private Boolean trainning;
	
	private Boolean barkingProblem;
	
	private Boolean passport;
	
	private Boolean playWithKids;
	
	private Boolean diseasesDisabilities;
	
	private Long category_id;
	
	private Set<MultipartFile> petsImages;
}
