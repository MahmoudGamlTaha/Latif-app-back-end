package com.commerce.backend.converter;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.FoodType;
import com.commerce.backend.constants.TrainningType;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Component
public class UserAdsConverter implements Function<UserAds, UserAdsVO> {
   
	@Autowired
	private UserRepository user;
    @Override
    public UserAdsVO apply(UserAds userAds) {
        return null;
    }

    public UserAds convertRequestToEntity(DynamicAdsRequest<String, String> request)
    {
        UserAds entity = null;
        if(request.getType() == AdsType.ACCESSORIES) {
            entity = new UserAccAds();
        }
        else if(request.getType() == AdsType.PET_CARE){
            entity = new UserMedicalAds();
        }
        else if(request.getType() == AdsType.PETS) {
            entity = new UserPetAds();
        }
        else if(request.getType() == AdsType.SERVICE) {
            entity = new UserServiceAds();
        }
        return convertToEntity(request, entity);
    }
    
    private UserAds convertToEntity(DynamicAdsRequest<String, String> request, UserAds entity) {
        List<HashMap<String, String>> data = request.getUserAds();
       
        HashMap<String, Object> hashedData = new HashMap<String, Object>();
        for(HashMap<String, String> d: data){
            hashedData.put(d.get("id"), d.get("value"));
        }
     
        entity.setName((String) hashedData.get("name"));
        entity.setCode((String) hashedData.get("code"));
        entity.setDescription((String) hashedData.get("description"));
        entity.setShortDescription(((String) hashedData.get("short_description")));
        entity.setActive(hashedData.get("active").toString().equalsIgnoreCase(String.valueOf(true)));
        
        entity.setType(request.getType());
        entity.setPrice(Float.parseFloat(String.valueOf(hashedData.get("price"))));
        entity.setLongitude((double) hashedData.get("longitude"));
        entity.setLatitude((double) hashedData.get("latitude"));
        entity.setCreatedAt((new Date()));
        entity.setUpdatedAt((new Date()));
        entity.setExternalLink(Boolean.parseBoolean(String.valueOf(getHashMapKeyWithCheck(hashedData, "external_link"))));
        User user = new User();
        user.setId(Long.parseLong(String.valueOf(hashedData.get("created_by"))));
        
        entity.setCreatedBy(user);

        if(request.getType() == AdsType.PETS || request.getType() == AdsType.Dogs)
        {
            PetCategory category = new PetCategory();
            category.setId(Long.parseLong(String.valueOf(hashedData.get("category_id"))));
     
            if(request.getType() == AdsType.Dogs) {
            ((UserPetAds)entity).setBarkingProblem(hashedData.get("barkingProblem").toString().equalsIgnoreCase(String.valueOf(true)));
            }
            ((UserPetAds)entity).setBreed((String) hashedData.get("breed"));
            ((UserPetAds)entity).setStock(Integer.parseInt((String) hashedData.get("stock")));
//            ((UserPetAds)entity).setWeaned(hashedData.get("weaned").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setWeaned(Boolean.parseBoolean(String.valueOf(hashedData.get("weaned")).toLowerCase()));
            ((UserPetAds)entity).setFood(String.valueOf(hashedData.get("food")));
            ((UserPetAds)entity).setDiseasesDisabilities(hashedData.get("diseasesDisabilities").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setDiseasesDisabilitiesDesc((String) hashedData.get("diseasesDisabilitiesDesc"));
            ((UserPetAds)entity).setNeutering(hashedData.get("neutering").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setTraining(TrainningType.valueOf( (String) hashedData.get("training")));
            ((UserPetAds)entity).setPlayWithKids(hashedData.get("playWithKids").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setPassport(hashedData.get("passport").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setVaccinationCertifcate(hashedData.get("vaccinationCertificate").toString().equalsIgnoreCase(String.valueOf(true)));
        }
       
        if(request.getType() == AdsType.ACCESSORIES) {
            assert entity instanceof UserAccAds;
            ((UserAccAds)entity).setUsed((Boolean) hashedData.get("used"));
			ItemCategory category = new ItemCategory();
			category.setId(Long.parseLong(String.valueOf(hashedData.get("category_id"))));
			//((UserAccAds)entity).setItemCategoryId(category);
        }
        else if(request.getType() == AdsType.PET_CARE) {
            ((UserMedicalAds)entity).setAllowServiceAtHome((Boolean) hashedData.get("allow_at_home"));
        }
        else if(request.getType() == AdsType.SERVICE) {
			((UserServiceAds)entity).setAllowServiceAtHome((Boolean) hashedData.get("allow_at_home"));
			ServiceCategory serviceCategory = new ServiceCategory();
			serviceCategory.setId(Long.parseLong(String.valueOf(hashedData.get("category_id"))));
            //((UserServiceAds)entity).setServiceCategory(serviceCategory);
            //((UserServiceAds)entity).setServiceCategoryType(serviceCategory);
        }
        return entity;
    }
    
    @SuppressWarnings("unused")
	private Valid validateHashMap(HashMap<String, Object> hashedData, AdsType type) {
    	return new Valid();
    }
    private Object getHashMapKeyWithCheck(HashMap<String, Object> hashedData, String key) {
    	if(hashedData.containsKey(key)) {
    		return hashedData.get(key);
    	}
    	return new Object();
    }
    private  class Valid{
       public boolean success;
       public String msg;
    }
    
}
