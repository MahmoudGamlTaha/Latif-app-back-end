package com.commerce.backend.converter;

import java.util.Date;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.AccessoriesCategory;
import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserMedicalAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;
import com.commerce.backend.model.request.userAds.UserAdsGeneralAdsRequest;
import com.commerce.backend.model.request.userAds.UserAdsRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.base.Function;

public class UserAdsToVoConverter implements Function<UserAds, UserAdsVO> {

	@Override
	public UserAdsVO apply(UserAds input) {
		UserAdsVO userAdsVo = null;
		if(input.getType() == AdsType.ACCESORIESS) {
			
		}
		else if(input.getType() == AdsType.PET_CARE){
			
		}
		else if(input.getType() == AdsType.PETS) {
			
		}
		else if(input.getType() == AdsType.SERVICE) {
			
		}
		
		
		return userAdsVo;
	}
	
	public UserAds transfromVOToEntity(UserAdsVO userAdsVo) {
		UserAds userAds = null;
		if(userAdsVo.getType() == AdsType.ACCESORIESS) {
			
		}
		else if(userAdsVo.getType() == AdsType.PET_CARE){
			
		}
		else if(userAdsVo.getType() == AdsType.PETS) {
			
		}
		else if(userAdsVo.getType() == AdsType.SERVICE) {
			
		}
		
		
		return userAds;
	}
	
	public UserAds transformRequestToEntity(UserAdsGeneralAdsRequest userAdsRequest) {
		UserAds userAds = null;
		if(userAdsRequest.getType() == AdsType.ACCESORIESS) {
			userAds = new UserAccAds();
			this.copyUserAdsObject(userAdsRequest, userAds);
		}
		else if(userAdsRequest.getType() == AdsType.PET_CARE){
			userAds = new UserMedicalAds();
		}
		else if(userAdsRequest.getType() == AdsType.PETS) {
			userAds = new UserPetAds();
		}
		else if(userAdsRequest.getType() == AdsType.SERVICE) {
			userAds = new UserServiceAds();
		}
		
		
		return userAds;
	}
	
	private UserAds copyUserAdsObject(UserAdsGeneralAdsRequest source, UserAds destination) {
		assert(source.getType() == destination.getType());
		destination.setActive(source.getUserAds().isActive());
		destination.setCode(source.getUserAds().getCode());
		destination.setDescription(source.getUserAds().getDescription());
		destination.setLatitude(source.getUserAds().getLatitude());
		destination.setLongitude(source.getUserAds().getLongitude());
		destination.setName(source.getUserAds().getName());
		destination.setShort_description(source.getUserAds().getShort_description());
		User user = new User();
		user.setId(source.getUserAds().getCreatedBy());
		destination.setCreatedBy(user);
		destination.setCreated_at(new Date());
		destination.setUpdated_at(new Date());
		//--------------------------------------------------------
		if(source.getType() == AdsType.ACCESORIESS) {
			((UserAccAds)destination).setUsed(source.getUserAccRequest().getUsed());
			ItemObjectCategory category = new ItemCategory();
			category.setId(source.getUserAccRequest().getCategory_id());
			((UserAccAds)destination).setItemCategoryId((ItemCategory)category);
		     category = new ItemCategory();
		     category.setId(source.getUserAccRequest().getCategory_type_id());
			((UserAccAds)destination).setItemCategoryType((ItemCategory)category);
		}
		if(source.getType() == AdsType.PETS) {
			((UserPetAds)destination).setBarkingProblem(source.getUserPetsAdsRequest().getBarkingProblem());
			PetCategory category = new PetCategory();
			category.setId(source.getUserPetsAdsRequest().getCategory());
			((UserPetAds)destination).setCategory(category);
	         category = new PetCategory();
	         category.setId(source.getUserPetsAdsRequest().getCategoryType());
			((UserPetAds)destination).setPetCategoryType(category);
			((UserPetAds)destination).setBreed(source.getUserPetsAdsRequest().getBreed());
			((UserPetAds)destination).setBarkingProblem(source.getUserPetsAdsRequest().getBarkingProblem());
			((UserPetAds)destination).setFood(source.getUserPetsAdsRequest().getFood());
			((UserPetAds)destination).setDiseasesDisabilities(source.getUserPetsAdsRequest().getDiseasesDisabilities());;
			((UserPetAds)destination).setDiseasesDisabilitiesDesc(source.getUserPetsAdsRequest().getDiseasesDisabilitiesDesc());
			((UserPetAds)destination).setNeutering(source.getUserPetsAdsRequest().getNeutering());
			((UserPetAds)destination).setTrainning(source.getUserPetsAdsRequest().getTrainning());
			((UserPetAds)destination).setPlayWithKids(source.getUserPetsAdsRequest().getPlayWithKids());
			((UserPetAds)destination).setPassport(source.getUserPetsAdsRequest().getPassport());
			((UserPetAds)destination).setVaccinationCertifcate(source.getUserPetsAdsRequest().getVaccinationCertifcate());;
			((UserPetAds)destination).setImage(source.getUserPetsAdsRequest().getImage().getName());
			
		}
		else if(source.getType() == AdsType.PET_CARE) {
			((UserMedicalAds)destination).setAllowServiceAtHome(source.getUserServiceAdsRequest().getAvaliable_at_home());
		}
		else if(source.getType() == AdsType.SERVICE) {
			((UserServiceAds)destination).setAllowServiceAtHome(source.getUserServiceAdsRequest().getAvaliable_at_home());
		}
		return destination;
	}
    
}
