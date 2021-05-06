package com.commerce.backend.converter;

import java.util.*;

import com.commerce.backend.helper.FieldsNames;
import com.commerce.backend.model.request.userAds.*;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.dao.ItemObjectCategoryRepository;
import com.commerce.backend.dao.ServiceCategoryRepository;
import com.commerce.backend.model.dto.ItemObjectCategoryVO;
import com.commerce.backend.model.dto.UserAccVO;
import com.commerce.backend.model.dto.UserAdsImageVO;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.dto.UserMedicalVO;
import com.commerce.backend.model.dto.UserPetAdsVO;
import com.commerce.backend.model.dto.UserServiceVO;
import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.MedicalCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.entity.ServiceCategory;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserAdsImage;
import com.commerce.backend.model.entity.UserMedicalAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;
import com.commerce.backend.model.request.userAds.UserAdsGeneralAdsRequest;

import java.util.function.Function;

import javax.transaction.Transactional;

@Component
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
		System.out.println("LLL"+UserAds.class.cast(source).getType());
		assert userAdsVo != null;
		this.copyUserAdsEntityToVo(UserAds.class.cast(source), userAdsVo);
		return userAdsVo;
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
		assert userAds != null;
		return copyUserAdsObject(userAdsRequest, userAds);
	}
    @Deprecated 
	private UserAds copyUserAdsObject(UserAdsGeneralAdsRequest source, UserAds destination) {
		//assert(source.getType() == destination.getType());

		destination.setActive(source.getUserAds().isActive());
		destination.setCode(source.getUserAds().getCode());
		destination.setDescription(source.getUserAds().getDescription());
		destination.setLatitude(source.getUserAds().getLatitude());
		destination.setLongitude(source.getUserAds().getLongitude());
		destination.setName(source.getUserAds().getName());
		destination.setShortDescription(source.getUserAds().getShort_description());
		destination.setPrice(source.getUserAds().getPrice());
		destination.setType(source.getType());
		User user = new User();
		user.setId(source.getUserAds().getCreatedBy());
		destination.setCreatedBy(user);
		destination.setCreatedAt(new Date());
		destination.setUpdatedAt(new Date());
		//--------------------------------------------------------
		if(source.getType() == AdsType.ACCESSORIES) {
		/*	UserAccAdsRequest UserAccAdsRequest = (UserAccAdsRequest) source.getUserAdsRequest();
			((UserAccAds)destination).setUsed(UserAccAdsRequest.getUsed());
			ItemObjectCategory category = new ItemCategory();
			category.setId(UserAccAdsRequest.getCategory_id());
			((UserAccAds)destination).setItemCategoryId((ItemCategory)category);
			category = new ItemCategory();
			category.setId(UserAccAdsRequest.getCategory_type_id());
			((UserAccAds)destination).setItemCategoryType((ItemCategory)category);*/
		}
		if(source.getType() == AdsType.PETS) {
			UserPetsAdsRequest UserPetsAdsRequest = (UserPetsAdsRequest) source.getUserAdsRequest();
			((UserPetAds)destination).setBarkingProblem(UserPetsAdsRequest.getBarkingProblem());
			/*PetCategory category = new PetCategory();
			category.setId(((UserPetsAdsRequest)source.getUserAdsRequest()).getCategory());
			((UserPetAds)destination).setCategory(category);
			category = new PetCategory();
			category.setId(((UserPetsAdsRequest)source.getUserAdsRequest()).getCategoryType());
			((UserPetAds)destination).setPetCategoryType(category);*/
			((UserPetAds)destination).setBreed(((UserPetsAdsRequest)source.getUserAdsRequest()).getBreed());
			((UserPetAds)destination).setBarkingProblem(((UserPetsAdsRequest)source.getUserAdsRequest()).getBarkingProblem());
			//((UserPetAds)destination).setFood(((UserPetsAdsRequest)source.getUserAdsRequest()).getFood());
			((UserPetAds)destination).setDiseasesDisabilities(((UserPetsAdsRequest)source.getUserAdsRequest()).getDiseasesDisabilities());;
			((UserPetAds)destination).setDiseasesDisabilitiesDesc(((UserPetsAdsRequest)source.getUserAdsRequest()).getDiseasesDisabilitiesDesc());
			((UserPetAds)destination).setNeutering(((UserPetsAdsRequest)source.getUserAdsRequest()).getNeutering());
			//((UserPetAds)destination).setTraining(UserPetsAdsRequest.getTraining());
			((UserPetAds)destination).setPlayWithKids(UserPetsAdsRequest.getPlayWithKids());
			((UserPetAds)destination).setPassport(UserPetsAdsRequest.getPassport());
		    ((UserPetAds)destination).setVaccinationCertifcate((UserPetsAdsRequest.getVaccinationCertificate()));;
			//((UserPetAds)destination).setImage(UserPetsAdsRequest.getImage().getName());


		}
		else if(source.getType() == AdsType.PET_CARE) {
			((UserMedicalAds)destination).setAllowServiceAtHome(((UserMedicalAdsRequest) source.getUserAdsRequest()).getAvaliable_at_home());
		}
		else if(source.getType() == AdsType.SERVICE) {
		/*	UserServiceAdsRequest UserServiceAdsRequest = (UserServiceAdsRequest) source.getUserAdsRequest();
			((UserServiceAds)destination).setAllowServiceAtHome(UserServiceAdsRequest.getAvaliable_at_home());
			ServiceCategory serviceCategory = new ServiceCategory();
			serviceCategory.setId((UserServiceAdsRequest.getCategory_id()));
				((UserServiceAds)destination).setServiceCategory(serviceCategory);
			UserServiceAdsRequest.setCategory_type_id(UserServiceAdsRequest.getCategory_type_id());*/
		//	((UserServiceAds)destination).setServiceCategory(serviceCategory);
	//		source.getUserServiceAdsRequest().setCategory_type_id(source.getUserServiceAdsRequest().getCategory_type_id());

		}
		return destination;
	}
 
	public UserAdsVO copyUserAdsEntityToVo(UserAds source, UserAdsVO destination) {
		//assert(source.getType() == destination.getType());
		
		//destination.setId(source.getId());
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
          
   	      //destination.setImages(imageVos);
		 }
		User user = new User();
		if(source.getCreatedBy() != null) {
			user.setId(source.getCreatedBy().getId());
			user.setFirstName(source.getCreatedBy().getFirstName());
			user.setLastName(source.getCreatedBy().getLastName());
			user.setAvatar(source.getCreatedBy().getAvatar());
			user.setPhone(source.getCreatedBy().getPhone());
		}
		destination.setCreatedBy(user);
		destination.setCreated_at(source.getCreatedAt());
		destination.setUpdated_at(source.getUpdatedAt());
		destination.setType(source.getType());
		if(source.getType() == AdsType.PETS || source.getType() == AdsType.Dogs) {
			((UserPetAdsVO)destination).setBarkingProblem(((UserPetAds)source).getBarkingProblem());
			//ItemObjectCategoryVO itemObjectCategoryVO = new ItemObjectCategoryVO(((UserPetAds)source).getCategory());
			//((UserPetAdsVO)destination).setCategory(itemObjectCategoryVO);
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
		  //  if(((UserPetAdsVO)destination).getImages() != null){
		//    	Optional<UserAdsImageVO> adsImage = ((UserPetAdsVO)destination).getImages().stream().findFirst();
		    //	if(adsImage.isPresent()) {
		    //	((UserPetAdsVO)destination).setImage(adsImage.get().getImage());
		    //	}
		    
		    //}
		    PetCategory category = ((UserPetAds)source).getCategory();
		    String categoryName = category == null ?null:category.getName();
		    /*if(categoryName != null) {
		    ((UserPetAdsVO)destination).setCategoryName(categoryName);
		    ((UserPetAdsVO)destination).setCategoryNameAr(category.getNameAr());
		    ((UserPetAdsVO)destination).setCategoryId(category.getId());
		    }*/
		}
		if(source.getType() == AdsType.SERVICE) {
			ServiceCategory category = ((UserServiceAds)source).getServiceCategory();
			 //ItemObjectCategory orgCategory = this.itemObjectCategoryRepository.findById(category.getId()).orElse(null);
		
			/*if(category != null) {
			((UserServiceVO)destination).setAllowAtHome(((UserServiceAds)source).getAllowServiceAtHome());
			((UserServiceVO)destination).setCategoryId(category.getId());
			((UserServiceVO)destination).setCategoryName(category.getName());
			((UserServiceVO)destination).setCategoryNameAr(category.getNameAr());
			}*/
		}
		else if(source.getType() == AdsType.PET_CARE) {
			((UserMedicalVO)destination).setAllowServiceAtHome(((UserMedicalAds)source).getAllowServiceAtHome());
			MedicalCategory category = ((UserMedicalAds)source).getMedicalCategoryType();
			/*(category != null) {
			((UserMedicalVO)destination).setAllowServiceAtHome(((UserMedicalAds)source).getAllowServiceAtHome());
			((UserMedicalVO)destination).setCategoryId(category.getId());
			((UserMedicalVO)destination).setCategoryName(category.getName());
			((UserMedicalVO)destination).setCategoryNameAr(category.getNameAr());
			}*/
		}
		else if(source.getType() == AdsType.ACCESSORIES) {
			((UserAccVO)destination).setUsed(((UserAccAds)source).getUsed());
		    ItemCategory itemCategory = ((UserAccAds)source).getItemCategoryId();
		    if(itemCategory != null) {
			/*((UserAccVO)destination).setCategoryId(itemCategory.getId());
			((UserAccVO)destination).setCategoryName(itemCategory.getNameAr());
			*/
			((UserAccVO)destination).setStock(((UserAccAds)source).getStock());
			
			ItemCategory category = ((UserAccAds)source).getItemCategoryId();
			((UserAccVO)destination).setAllowServiceAtHome(((UserAccAds)source).getAllowServiceAtHome());
			if(category != null) {
			/*((UserAccVO)destination).setCategoryId(category.getId());
			((UserAccVO)destination).setCategoryName(category.getName());
			((UserAccVO)destination).setCategoryNameAr(category.getNameAr());*/
			}
		    }
		}
		else if(source.getType() == AdsType.DELIVERY) {
			
		}
		 
		return destination;
	}

	public UserAdsVO convertToVo(UserAds entity){
		UserAdsVO userAdsVo = new UserAdsVO();

		userAdsVo.setId(new KeyResponse(FieldsNames.id, FieldsNames.id_ar, entity.getId()));
		userAdsVo.setName(new KeyResponse(FieldsNames.name, FieldsNames.name_ar, entity.getName()));
		userAdsVo.setCode(new KeyResponse(FieldsNames.code, FieldsNames.code_ar, entity.getCode()));
		userAdsVo.setCity(new KeyResponse(FieldsNames.city, FieldsNames.code_ar, entity.getCity()));
		userAdsVo.setDescription(new KeyResponse(FieldsNames.desc, FieldsNames.desc_ar, entity.getDescription()));
		userAdsVo.setShort_description(new KeyResponse(FieldsNames.short_desc, FieldsNames.short_desc_ar, entity.getShortDescription()));
		userAdsVo.setActive(new KeyResponse(FieldsNames.active, FieldsNames.active_ar, entity.isActive()));
		userAdsVo.setType(new KeyResponse(FieldsNames.type, FieldsNames.type_ar, entity.getType()));
		userAdsVo.setPrice(new KeyResponse(FieldsNames.price, FieldsNames.price_ar, entity.getPrice()));
		userAdsVo.setLongitude(new KeyResponse(FieldsNames.longitude, FieldsNames.longitude_ar, entity.getLongitude()));
		userAdsVo.setLatitude(new KeyResponse(FieldsNames.longitude, FieldsNames.latitude_ar, entity.getLatitude()));
		userAdsVo.setCreated_at(new KeyResponse(FieldsNames.createdAt, FieldsNames.createdAt_ar, entity.getLatitude()));
		userAdsVo.setUpdated_at(new KeyResponse(FieldsNames.updatedAt, FieldsNames.updatedAt_ar, entity.getUpdatedAt()));
		userAdsVo.setExternal_link(new KeyResponse(FieldsNames.externalLink, FieldsNames.externalLink_ar, entity.getExternalLink()));
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
			userAdsVo.setImages(new KeyResponse(FieldsNames.image, FieldsNames.image_ar, imageVos));
		}
		if(entity.getCreatedBy() != null) {
			User user = new User();
			user.setId(entity.getCreatedBy().getId());
			user.setFirstName(entity.getCreatedBy().getFirstName());
			user.setLastName(entity.getCreatedBy().getLastName());
			user.setAvatar(entity.getCreatedBy().getAvatar());
			user.setPhone(entity.getCreatedBy().getPhone());
			userAdsVo.setCreatedBy(new KeyResponse(FieldsNames.createdBy, FieldsNames.createdBy_ar, user));
		}
		List<Object> extraInfo = new ArrayList<>();
		if(entity.getType() == AdsType.PETS) {
			extraInfo.add(new KeyResponse(FieldsNames.barkingProblem, FieldsNames.barkingProblem_ar, ((UserPetAds)entity).getBarkingProblem()));
			extraInfo.add(new KeyResponse(FieldsNames.bread, FieldsNames.bread_ar, ((UserPetAds)entity).getBreed()));
			extraInfo.add(new KeyResponse(FieldsNames.food, FieldsNames.food_ar, ((UserPetAds)entity).getFood()));
			extraInfo.add(new KeyResponse(FieldsNames.diseasesOrDisabilities, FieldsNames.diseasesOrDisabilities_ar, ((UserPetAds)entity).getDiseasesDisabilities()));
			extraInfo.add(new KeyResponse(FieldsNames.diseasesOrDisabilitiesDesc, FieldsNames.diseasesOrDisabilitiesDesc_ar, ((UserPetAds)entity).getDiseasesDisabilitiesDesc()));
			extraInfo.add(new KeyResponse(FieldsNames.Neutering, FieldsNames.Neutering_ar, ((UserPetAds)entity).getNeutering()));
			extraInfo.add(new KeyResponse(FieldsNames.training, FieldsNames.training_ar, ((UserPetAds)entity).getTraining()));
			extraInfo.add(new KeyResponse(FieldsNames.PlayWithKids, FieldsNames.PlayWithKids_ar, ((UserPetAds)entity).getPlayWithKids()));
			extraInfo.add(new KeyResponse(FieldsNames.Passport, FieldsNames.Passport_ar, ((UserPetAds)entity).getPassport()));
			extraInfo.add(new KeyResponse(FieldsNames.VaccinationCertificate, FieldsNames.VaccinationCertificate_ar, ((UserPetAds)entity).getVaccinationCertifcate()));
			extraInfo.add(new KeyResponse(FieldsNames.weaned, FieldsNames.weaned_ar, ((UserPetAds)entity).getWeaned()));
			extraInfo.add(new KeyResponse(FieldsNames.Stock, FieldsNames.Stock_ar, ((UserPetAds)entity).getStock()));
			//extraInfo.add(new KeyResponse(FieldsNames.Category, FieldsNames.Category_ar, ((UserPetAds) entity).getCategory()));
		}else if(entity.getType() == AdsType.SERVICE) {
			extraInfo.add(new KeyResponse(FieldsNames.AllowAtHome, FieldsNames.AllowAtHome_ar, ((UserServiceAds)entity).getAllowServiceAtHome()));
			//extraInfo.add(new KeyResponse(FieldsNames.Category, FieldsNames.Category_ar, ((UserServiceAds) entity).getServiceCategory()));
		}else if(entity.getType() == AdsType.PET_CARE) {
			extraInfo.add(new KeyResponse(FieldsNames.AllowAtHome, FieldsNames.AllowAtHome_ar, ((UserMedicalAds)entity).getAllowServiceAtHome()));
			//extraInfo.add(new KeyResponse(FieldsNames.Category, FieldsNames.Category_ar, ((UserMedicalAds) entity).getMedicalCategoryType()));
		}else if(entity.getType() == AdsType.ACCESSORIES) {
			extraInfo.add(new KeyResponse(FieldsNames.AllowAtHome, FieldsNames.AllowAtHome_ar, ((UserAccAds)entity).getAllowServiceAtHome()));
			extraInfo.add(new KeyResponse("used", "مستعمل", ((UserAccAds)entity).getUsed()));
			//extraInfo.add(new KeyResponse(FieldsNames.Category, FieldsNames.Category_ar, ((UserAccAds) entity).getItemCategoryId()));
		}
		userAdsVo.setExtra(extraInfo);
		return userAdsVo;
	}
}
