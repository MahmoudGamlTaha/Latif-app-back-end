package com.commerce.backend.model.dto;


import com.commerce.backend.constants.TrainningType;

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

private String training;

private Boolean barkingProblem;

private Boolean passport;

private Boolean playWithKids;

private Boolean diseasesDisabilities;

private String diseasesDisabilitiesDesc;

private ItemObjectCategoryVO category;

private String food;
private String selling_type;
}
