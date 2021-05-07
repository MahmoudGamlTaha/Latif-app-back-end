package com.commerce.backend.converter;

import java.util.*;

import com.commerce.backend.helper.FieldsNames;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.dao.ItemObjectCategoryRepository;
import com.commerce.backend.model.dto.ItemObjectCategoryVO;
import com.commerce.backend.model.dto.UserAccVO;
import com.commerce.backend.model.dto.UserAdsImageVO;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.dto.UserMedicalVO;
import com.commerce.backend.model.dto.UserPetAdsVO;
import com.commerce.backend.model.dto.UserServiceVO;
import com.commerce.backend.model.dto.UserVO;
import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.MedicalCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.entity.ServiceCategory;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserAdsImage;
import com.commerce.backend.model.entity.UserMedicalAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;

import java.util.function.Function;
import javax.transaction.Transactional;

@Component
@Transactional
public class UserAdsToVoConverter implements Function< UserAds, UserAdsVO> {
    @Autowired
	ItemObjectCategoryRepository itemObjectCategoryRepository;
    
	@Override
	 public UserAdsVO apply( UserAds source) {
		UserAdsVO userAdsVo = null;
       if(source == null) {
    	   return userAdsVo;
       }
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
		System.out.println(UserAds.class.cast(source).getType());
		assert userAdsVo != null;
		return convertToVo(source);
	}

	public UserAdsVO copyUserAdsEntityToVo(UserAds source, UserAdsVO destination) {
		//assert(source.getType() == destination.getType());

		destination.setId(source.getId());
		destination.setActive(source.isActive());
		destination.setCode(source.getCode());
		destination.setDescription(source.getDescription());
		destination.setLatitude(source.getLatitude());
		destination.setLongitude(source.getLongitude());
		destination.setName(source.getName());
		destination.setShort_description(source.getShortDescription());
		destination.setPrice(source.getPrice());
		destination.setExternal_link(source.getExternalLink());
		 Set<UserAdsImage> images = source.getUserAdsImage();
		if(images != null) {
			//Hibernate.initialize(images);
			 Set<UserAdsImageVO> imageVos = new HashSet<UserAdsImageVO>();
			 images.stream().forEach(img ->{
				 UserAdsImageVO imgVo = new UserAdsImageVO();
				 imgVo.setId(img.getId());
				 imgVo.setImage(img.getImage());
				 imgVo.setExternal_link(source.getExternalLink());
				 imgVo.setUserAdsId(source.getId());
			     imageVos.add(imgVo);
			 });
          
   	        destination.setImages(imageVos);
		 }
		UserVO user = new UserVO();
		if(source.getCreatedBy() != null) {
			user.setId(source.getCreatedBy().getId());
			user.setFirstName(source.getCreatedBy().getFirstName());
			user.setLastName(source.getCreatedBy().getLastName());
			user.setAvatar(source.getCreatedBy().getAvatar());
			user.setPhone(source.getCreatedBy().getPhone());
			user.setRegistrationDate(source.getCreatedBy().getRegistrationDate());
			Set<UserAds> ads = source.getCreatedBy().getAds();
			user.setAdsCount(ads==null?0:ads.size());
		}
		destination.setCreatedBy(user);
		destination.setCreated_at(source.getCreatedAt());
		destination.setUpdated_at(source.getUpdatedAt());
		destination.setType(source.getType());
		if(source.getType() == AdsType.PETS || source.getType() == AdsType.Dogs) {
			((UserPetAdsVO)destination).setBarkingProblem(((UserPetAds)source).getBarkingProblem());
			 ItemObjectCategoryVO itemObjectCategoryVO = new ItemObjectCategoryVO(((UserPetAds)source).getCategory());
			((UserPetAdsVO)destination).setCategory(itemObjectCategoryVO);
			((UserPetAdsVO)destination).setBreed(((UserPetAds)source).getBreed());
			((UserPetAdsVO)destination).setBarkingProblem(((UserPetAds)source).getBarkingProblem());
			((UserPetAdsVO)destination).setFood(((UserPetAds)source).getFood());
			((UserPetAdsVO)destination).setDiseasesDisabilities(((UserPetAds)source).getDiseasesDisabilities());;
			((UserPetAdsVO)destination).setDiseasesDisabilitiesDesc(((UserPetAds)source).getDiseasesDisabilitiesDesc());
			((UserPetAdsVO)destination).setNeutering(((UserPetAds)source).getNeutering());
			((UserPetAdsVO)destination).setTraining(((UserPetAds)source).getTraining());
			((UserPetAdsVO)destination).setPlayWithKids(((UserPetAds)source).getPlayWithKids());
			((UserPetAdsVO)destination).setPassport(((UserPetAds)source).getPassport());
		  ((UserPetAdsVO)destination).setVaccinationCertificate(((UserPetAds)source).getVaccinationCertifcate());;
		  ((UserPetAdsVO)destination).setWeaned(((UserPetAds)source).getWeaned());
		  ((UserPetAdsVO)destination).setStock(((UserPetAdsVO)destination).getStock());

		    if(((UserPetAdsVO)destination).getImages() != null){
		    	Optional<UserAdsImageVO> adsImage = ((UserPetAdsVO)destination).getImages().stream().findFirst();
		    	if(adsImage.isPresent()) {
		    	((UserPetAdsVO)destination).setImage(adsImage.get().getImage());
		    	}
		    }
		    PetCategory category = (PetCategory)((UserPetAds)source).getCategory();

		    String categoryName = category == null ?null:category.getName();
		    if(categoryName != null) {
		    ((UserPetAdsVO)destination).setCategoryName(categoryName);
		    ((UserPetAdsVO)destination).setCategoryNameAr(category.getNameAr());
		    ((UserPetAdsVO)destination).setCategoryId(category.getId());
		    }
		}
		if(source.getType() == AdsType.SERVICE) {

			ServiceCategory category = (ServiceCategory)((UserServiceAds)source).getCategory();
			if(category != null) {

			((UserServiceVO)destination).setAllowAtHome(((UserServiceAds)source).getAllowServiceAtHome());
			((UserServiceVO)destination).setCategoryId(category.getId());
			((UserServiceVO)destination).setCategoryName(category.getName());
			((UserServiceVO)destination).setCategoryNameAr(category.getNameAr());
			}
		}
		else if(source.getType() == AdsType.PET_CARE) {
			((UserMedicalVO)destination).setAllowServiceAtHome(((UserMedicalAds)source).getAllowServiceAtHome());

			MedicalCategory category = (MedicalCategory)((UserMedicalAds)source).getCategory();
			if(category != null) {

			((UserMedicalVO)destination).setAllowServiceAtHome(((UserMedicalAds)source).getAllowServiceAtHome());
			((UserMedicalVO)destination).setCategoryId(category.getId());
			((UserMedicalVO)destination).setCategoryName(category.getName());
			((UserMedicalVO)destination).setCategoryNameAr(category.getNameAr());
			}
		}
		else if(source.getType() == AdsType.ACCESSORIES) {
			((UserAccVO)destination).setUsed(((UserAccAds)source).getUsed());
			ItemObjectCategory va = ((UserAccAds)source).getCategory();
		    ItemCategory itemCategory = (ItemCategory)((UserAccAds)source).getCategory();
			Hibernate.initialize(itemCategory);
			((UserAccVO)destination).setStock(((UserAccAds)source).getStock());
      ((UserAccVO)destination).setAllowServiceAtHome(((UserAccAds)source).getAllowServiceAtHome());
      if(itemCategory != null) {
			((UserAccVO)destination).setCategoryId(itemCategory.getId());
			((UserAccVO)destination).setCategoryName(itemCategory.getName());
			((UserAccVO)destination).setCategoryNameAr(itemCategory.getNameAr());
		    }
		}
		else if(source.getType() == AdsType.DELIVERY) {
			
		}
		 
		return destination;
	}


	public UserAdsVO convertToVo(UserAds entity){
		UserAdsVO userAdsVo = new UserAdsVO();

		/*userAdsVo.setId(new KeyResponse(FieldsNames.id, FieldsNames.id_ar, entity.getId()));
		userAdsVo.setName(new KeyResponse(FieldsNames.name, FieldsNames.name_ar, entity.getName()));
		userAdsVo.setCode(new KeyResponse(FieldsNames.code, FieldsNames.code_ar, entity.getCode()));
		userAdsVo.setCity(new KeyResponse(FieldsNames.city, FieldsNames.code_ar, entity.getCity()));
		userAdsVo.setDescription(new KeyResponse(FieldsNames.desc, FieldsNames.desc_ar, entity.getDescription()));
		userAdsVo.setShort_description(new KeyResponse(FieldsNames.short_desc, FieldsNames.short_desc_ar, entity.getShortDescription()));
	//	userAdsVo.setActive(new KeyResponse(FieldsNames.active, FieldsNames.active_ar, entity.isActive()));

		userAdsVo.setType(new KeyResponse(FieldsNames.type, FieldsNames.type_ar, entity.getType()));
		userAdsVo.setPrice(new KeyResponse(FieldsNames.price, FieldsNames.price_ar, entity.getPrice()));
		userAdsVo.setLongitude(new KeyResponse(FieldsNames.longitude, FieldsNames.longitude_ar, entity.getLongitude()));
		userAdsVo.setLatitude(new KeyResponse(FieldsNames.longitude, FieldsNames.latitude_ar, entity.getLatitude()));
		userAdsVo.setCreated_at(new KeyResponse(FieldsNames.createdAt, FieldsNames.createdAt_ar, entity.getLatitude()));
		userAdsVo.setUpdated_at(new KeyResponse(FieldsNames.updatedAt, FieldsNames.updatedAt_ar, entity.getUpdatedAt()));
		userAdsVo.setExternal_link(new KeyResponse(FieldsNames.externalLink, FieldsNames.externalLink_ar, entity.getExternalLink()));*/
		userAdsVo.setId(entity.getId());
		userAdsVo.setName(entity.getName());
		userAdsVo.setCode(entity.getCode());
		userAdsVo.setActive(entity.isActive());
		userAdsVo.setCity(entity.getCity());
		userAdsVo.setDescription(entity.getDescription());
		userAdsVo.setShort_description(entity.getShortDescription());
		userAdsVo.setType(entity.getType());
		userAdsVo.setPrice(entity.getPrice());
		userAdsVo.setLongitude(entity.getLongitude());
		userAdsVo.setCreated_at(entity.getCreatedAt());
		userAdsVo.setLatitude(entity.getLatitude());
		userAdsVo.setUpdated_at(entity.getUpdatedAt());
		userAdsVo.setExternal_link(Boolean.parseBoolean(checkValue(entity.getExternalLink(), SystemConstant.BOOLEAN).toString()));
		Set<UserAdsImage> images = entity.getUserAdsImage();
		if(images != null) {
			Set<UserAdsImageVO> imageVos = new HashSet<UserAdsImageVO>();
			images.stream().forEach(img ->{
				UserAdsImageVO imgVo = new UserAdsImageVO();
				imgVo.setId(img.getId());
				imgVo.setImage(img.getImage());
				imgVo.setExternal_link(entity.getExternalLink());
				imgVo.setUserAdsId(entity.getId());
				imageVos.add(imgVo);
			});
			userAdsVo.setImages(imageVos);
		}
		if(entity.getCreatedBy() != null) {
			UserVO user = new UserVO();
			user.setId(entity.getCreatedBy().getId());
			user.setFirstName(entity.getCreatedBy().getFirstName());
			user.setLastName(entity.getCreatedBy().getLastName());
			user.setAvatar(entity.getCreatedBy().getAvatar());
			user.setPhone(entity.getCreatedBy().getPhone());
			user.setRegistrationDate(entity.getCreatedAt());
			Set<UserAds> ads = entity.getCreatedBy().getAds();
			if(ads != null) {
			user.setAdsCount(ads.size());
			}else {
				user.setAdsCount(0);
			}
			userAdsVo.setCreatedBy(user);
		}
		List<Object> extraInfo = new ArrayList<>();
		if(entity.getType() == AdsType.PETS) {
			extraInfo.add(new KeyResponse(FieldsNames.barkingProblem, FieldsNames.barkingProblem_ar, checkValue(((UserPetAds)entity).getBarkingProblem(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.bread, FieldsNames.bread_ar, checkValue(((UserPetAds)entity).getBreed(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.food, FieldsNames.food_ar, ((UserPetAds)entity).getFood()));
			extraInfo.add(new KeyResponse(FieldsNames.diseasesOrDisabilities, FieldsNames.diseasesOrDisabilities_ar, checkValue(((UserPetAds)entity).getDiseasesDisabilities(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.diseasesOrDisabilitiesDesc, FieldsNames.diseasesOrDisabilitiesDesc_ar, checkValue(((UserPetAds)entity).getDiseasesDisabilitiesDesc(), SystemConstant.STRING)));
			extraInfo.add(new KeyResponse(FieldsNames.Neutering, FieldsNames.Neutering_ar, checkValue(((UserPetAds)entity).getNeutering(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.training, FieldsNames.training_ar, ((UserPetAds)entity).getTraining()));
			extraInfo.add(new KeyResponse(FieldsNames.PlayWithKids, FieldsNames.PlayWithKids_ar, checkValue(((UserPetAds)entity).getPlayWithKids(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.Passport, FieldsNames.Passport_ar, checkValue(((UserPetAds)entity).getPassport(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.VaccinationCertificate, FieldsNames.VaccinationCertificate_ar, checkValue(((UserPetAds)entity).getVaccinationCertifcate(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.weaned, FieldsNames.weaned_ar, checkValue(((UserPetAds)entity).getWeaned(), SystemConstant.BOOLEAN)));
			extraInfo.add(new KeyResponse(FieldsNames.Stock, FieldsNames.Stock_ar, ((UserPetAds)entity).getStock()));
			PetCategory category = (PetCategory)(entity).getCategory();
			String categoryName = category == null ?null:category.getName();
			if(categoryName != null) {
				userAdsVo.setCategoryName(categoryName);
				userAdsVo.setCategoryNameAr(category.getNameAr());
				userAdsVo.setCategoryId(category.getId());
				
			}
		}else if(entity.getType() == AdsType.SERVICE) {
			extraInfo.add(new KeyResponse(FieldsNames.AllowAtHome, FieldsNames.AllowAtHome_ar, ((UserServiceAds)entity).getAllowServiceAtHome()));
			ServiceCategory category = (ServiceCategory)((UserServiceAds)entity).getCategory();
			String categoryName = category == null ?null:category.getName();
			if(categoryName != null) {
				userAdsVo.setCategoryName(categoryName);
				userAdsVo.setCategoryNameAr(category.getNameAr());
				userAdsVo.setCategoryId(category.getId());
			}
		}else if(entity.getType() == AdsType.PET_CARE) {
			extraInfo.add(new KeyResponse(FieldsNames.AllowAtHome, FieldsNames.AllowAtHome_ar, ((UserMedicalAds)entity).getAllowServiceAtHome()));
			MedicalCategory category = (MedicalCategory)((UserMedicalAds)entity).getCategory();
			String categoryName = category == null ?null:category.getName();
			if(categoryName != null) {
				userAdsVo.setCategoryName(categoryName);
				userAdsVo.setCategoryNameAr(category.getNameAr());
				userAdsVo.setCategoryId(category.getId());
			}
		}else if(entity.getType() == AdsType.ACCESSORIES) {
			extraInfo.add(new KeyResponse(FieldsNames.AllowAtHome, FieldsNames.AllowAtHome_ar, ((UserAccAds)entity).getAllowServiceAtHome()));
			extraInfo.add(new KeyResponse(FieldsNames.used, FieldsNames.used_ar, ((UserAccAds)entity).getUsed()));
			ItemCategory category = (ItemCategory)((UserAccAds)entity).getCategory();
			String categoryName = category == null ?null:category.getName();
			if(categoryName != null) {
				userAdsVo.setCategoryName(categoryName);
				userAdsVo.setCategoryNameAr(category.getNameAr());
				userAdsVo.setCategoryId(category.getId());
			}
		}
		userAdsVo.setExtra(extraInfo);
		return userAdsVo;
	}
	public Object checkValue(Object b, Integer type) {
		if(type == SystemConstant.BOOLEAN && b == null) {
			return false;
		}
		
		if(type == SystemConstant.STRING && b == null) {
			return "N/A";
		}
		return b;
	}

}
