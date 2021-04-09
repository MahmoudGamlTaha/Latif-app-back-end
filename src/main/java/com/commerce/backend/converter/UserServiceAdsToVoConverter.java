package com.commerce.backend.converter;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.ItemObjectCategoryVO;
import com.commerce.backend.model.dto.UserAccVO;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.dto.UserMedicalVO;
import com.commerce.backend.model.dto.UserPetAdsVO;
import com.commerce.backend.model.dto.UserServiceVO;
import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.entity.ServiceCategory;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserMedicalAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;
import com.commerce.backend.model.request.userAds.UserAdsGeneralAdsRequest;
import com.google.common.base.Function;
@Deprecated
@Component
public class UserServiceAdsToVoConverter implements Function<Class<? extends UserServiceAds>, UserAdsVO> {

	
	@Override
	 public UserAdsVO apply(Class<? extends UserServiceAds> source) {
		UserAdsVO userAdsVo = null;
      try {
		if(UserAds.class.cast(source).getType() == AdsType.ACCESSORIES) {
			userAdsVo = new UserAccVO();
		}
		else if(UserAds.class.cast(source).getType() == AdsType.PET_CARE){
			userAdsVo = new UserMedicalVO();
		}
		else if(UserAds.class.cast(source).getType() == AdsType.PETS) {
			userAdsVo = new UserPetAdsVO();
		}
		else if(UserAds.class.cast(source).getType() == AdsType.SERVICE) {
			userAdsVo = new UserServiceVO();
		}
		this.copyUserAdsEntityToVo(UserAds.class.cast(source), userAdsVo);
		return userAdsVo;
      }catch(Exception ex) {
    	  ex.printStackTrace();
    	  return null;
      }
	}
	
	@Deprecated
	public UserAds transfromVOToEntity(UserAdsVO userAdsVo) {
		UserAds userAds = null;
		if(userAdsVo.getType() == AdsType.ACCESSORIES) {
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
		if(userAdsRequest.getType() == AdsType.ACCESSORIES) {
			userAds = new UserAccAds();
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
		  userAds = this.copyUserAdsObject(userAdsRequest, userAds);
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
		destination.setShortDescription(source.getUserAds().getShort_description());
		destination.setPrice(source.getUserAds().getPrice());
		User user = new User();
		user.setId(source.getUserAds().getCreatedBy());
		//destination.setCreatedBy(user);
		destination.setCreatedAt(new Date());
		destination.setUpdatedAt(new Date());
		//--------------------------------------------------------
		if(source.getType() == AdsType.ACCESSORIES) {
		//	((UserAccAds)destination).setUsed(source.getUserAccRequest().getUsed());
			ItemObjectCategory category = new ItemCategory();
			//category.setId(source.getUserAccRequest().getCategory_id());
			//((UserAccAds)destination).setItemCategoryId((ItemCategory)category);
		     category = new ItemCategory();
		     //category.setId(source.getUserAccRequest().getCategory_type_id());
			//((UserAccAds)destination).setItemCategoryType((ItemCategory)category);
		}
		if(source.getType() == AdsType.PETS) {
		//	((UserPetAds)destination).setBarkingProblem(source.getUserPetsAdsRequest().getBarkingProblem());
			//PetCategory category = new PetCategory();
			//category.setId(source.getUserPetsAdsRequest().getCategory());
			//((UserPetAds)destination).setCategory(category);
	         //category = new PetCategory();
	        // category.setId(source.getUserPetsAdsRequest().getCategoryType());
			//((UserPetAds)destination).setPetCategoryType(category);
			/*((UserPetAds)destination).setBreed(source.getUserPetsAdsRequest().getBreed());
			((UserPetAds)destination).setBarkingProblem(source.getUserPetsAdsRequest().getBarkingProblem());
			((UserPetAds)destination).setFood(source.getUserPetsAdsRequest().getFood());
			((UserPetAds)destination).setDiseasesDisabilities(source.getUserPetsAdsRequest().getDiseasesDisabilities());;
			((UserPetAds)destination).setDiseasesDisabilitiesDesc(source.getUserPetsAdsRequest().getDiseasesDisabilitiesDesc());
			((UserPetAds)destination).setNeutering(source.getUserPetsAdsRequest().getNeutering());
			((UserPetAds)destination).setTraining(source.getUserPetsAdsRequest().getTrainning());
			((UserPetAds)destination).setPlayWithKids(source.getUserPetsAdsRequest().getPlayWithKids());
			((UserPetAds)destination).setPassport(source.getUserPetsAdsRequest().getPassport());
			((UserPetAds)destination).setVaccinationCertifcate(source.getUserPetsAdsRequest().getVaccinationCertifcate());;
			((UserPetAds)destination).setImage(source.getUserPetsAdsRequest().getImage().getName());
			*/
		}
		else if(source.getType() == AdsType.PET_CARE) {
		//	((UserMedicalAds)destination).setAllowServiceAtHome(source.getUserServiceAdsRequest().getAvaliable_at_home());
		}
		else if(source.getType() == AdsType.SERVICE) {
			//((UserServiceAds)destination).setAllowServiceAtHome(source.getUserServiceAdsRequest().getAvaliable_at_home());
			  ServiceCategory serviceCategory = new ServiceCategory();
			//  serviceCategory.setId((source.getUserServiceAdsRequest().getCategory_id()));
		//	((UserServiceAds)destination).setServiceCategory(serviceCategory);
			//source.getUserServiceAdsRequest().setCategory_type_id(source.getUserServiceAdsRequest().getCategory_type_id());
		}
		return destination;
	}
	
	public UserAdsVO copyUserAdsEntityToVo(UserAds source, UserAdsVO destination) {
		//assert(source.getType() == destination.getType());
		destination.setActive(source.isActive());
		destination.setCode(source.getCode());
		destination.setDescription(source.getDescription());
		destination.setLatitude(source.getLatitude());
		destination.setLongitude(source.getLongitude());
		destination.setName(source.getName());
		destination.setShort_description(source.getShortDescription());
		destination.setPrice(source.getPrice());
		User user = new User();
	//	user.setId(source.getCreatedBy().getId());
		destination.setCreatedBy(user);
		destination.setCreated_at(new Date());
		destination.setUpdated_at(new Date());
		
		if(source.getType() == AdsType.PETS) {
			
			((UserPetAdsVO)destination).setBarkingProblem(((UserPetAds)source).getBarkingProblem());
			//ItemObjectCategoryVO itemObjectCategoryVO = new ItemObjectCategoryVO(((UserPetAds)source).getCategory());
			//((UserPetAdsVO)destination).setCategory(itemObjectCategoryVO);
			((UserPetAdsVO)destination).setBreed(((UserPetAds)source).getBreed());
			((UserPetAdsVO)destination).setBarkingProblem(((UserPetAds)source).getBarkingProblem());
			//((UserPetAdsVO)destination).setFood(((UserPetAds)source).getFood());
			((UserPetAdsVO)destination).setDiseasesDisabilities(((UserPetAds)source).getDiseasesDisabilities());;
			((UserPetAdsVO)destination).setDiseasesDisabilitiesDesc(((UserPetAds)source).getDiseasesDisabilitiesDesc());
			((UserPetAdsVO)destination).setNeutering(((UserPetAds)source).getNeutering());
		//	((UserPetAdsVO)destination).setTrainning(((UserPetAds)source).getTraining());
			((UserPetAdsVO)destination).setPlayWithKids(((UserPetAds)source).getPlayWithKids());
			((UserPetAdsVO)destination).setPassport(((UserPetAds)source).getPassport());
			//((UserPetAdsVO)destination).setVaccinationCertifcate(((UserPetAds)source).getVaccinationCertifcate());;
			((UserPetAdsVO)destination).setImage(((UserPetAds)source).getImage());
		}
		return destination;
	}


    
}
