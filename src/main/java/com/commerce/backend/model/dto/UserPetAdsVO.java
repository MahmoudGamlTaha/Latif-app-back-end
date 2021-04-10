package com.commerce.backend.model.dto;

import java.util.Set;

import com.commerce.backend.constants.FoodType;
import com.commerce.backend.constants.TrainningType;
import com.commerce.backend.model.entity.UserAdsImage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserPetAdsVO extends UserAdsVO{

private String image;

private Float price;

private String breed;

private Integer stock;

private Boolean weaned;

private Boolean neutering;

private Boolean vaccinationCertificate;

private TrainningType training;

private Boolean barkingProblem;

private Boolean passport;

private Boolean playWithKids;

private Boolean diseasesDisabilities;

private String diseasesDisabilitiesDesc;

private ItemObjectCategoryVO category;

private String food;

private Set<UserAdsImage> petsImages;


}
