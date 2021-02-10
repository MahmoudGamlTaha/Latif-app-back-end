package com.commerce.backend.model.dto;

import java.util.Set;


import com.commerce.backend.model.entity.UserAdsImage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class UserPetAdsVO extends UserAdsVO{

private String image;

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

private ItemObjectCategoryVO category;

private Set<UserAdsImage> petsImages; 

}
