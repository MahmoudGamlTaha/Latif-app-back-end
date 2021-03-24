package com.commerce.backend.converter;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.FoodType;
import com.commerce.backend.constants.TrainningType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Function;

@Component
public class UserAdsConverter implements Function<UserAds, UserAdsVO> {

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

        LinkedList<HashMap<String, String>> data = request.getUserAds();
        HashMap<String, Object> hashedData = new HashMap<String, Object>();
        for(HashMap<String, String> d: data){
            hashedData.put(d.get("id"), d.get("value"));
        }
        entity.setName((String) hashedData.get("name"));
        entity.setCode((String) hashedData.get("code"));
        entity.setDescription((String) hashedData.get("description"));
        entity.setShort_description((String) hashedData.get("short_description"));
        entity.setActive(hashedData.get("active").toString().equalsIgnoreCase(String.valueOf(true)));
        entity.setType(AdsType.valueOf((String)hashedData.get("type")));
        entity.setPrice(Float.parseFloat(String.valueOf(hashedData.get("price"))));
        entity.setLongitude((String) hashedData.get("longitude"));
        entity.setLatitude((String) hashedData.get("latitude"));
        entity.setCreated_at(new Date());
        entity.setUpdated_at(new Date());
        User user = new User();
        user.setId(Long.parseLong(String.valueOf(hashedData.get("created_by"))));
        entity.setCreatedBy(user);

        if(request.getType() == AdsType.PETS)
        {
            PetCategory category = new PetCategory();
            category.setId(Long.parseLong(String.valueOf(hashedData.get("category_id"))));
            ((UserPetAds)entity).setCategory(category);
            category = new PetCategory();
            category.setId(Long.parseLong(String.valueOf(hashedData.get("category_id"))));
            ((UserPetAds)entity).setPetCategoryType(category);
            ((UserPetAds)entity).setBarkingProblem(hashedData.get("barkingProblem").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setBreed((String) hashedData.get("breed"));
            ((UserPetAds)entity).setStock(Integer.parseInt((String) hashedData.get("stock")));
            ((UserPetAds)entity).setWeaned(hashedData.get("weaned").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setFood(FoodType.valueOf((String) hashedData.get("food")));
            ((UserPetAds)entity).setDiseasesDisabilities(hashedData.get("diseasesDisabilities").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setDiseasesDisabilitiesDesc((String) hashedData.get("diseasesDisabilitiesDesc"));
            ((UserPetAds)entity).setNeutering(hashedData.get("neutering").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setTraining(TrainningType.valueOf((String) hashedData.get("training")));
            ((UserPetAds)entity).setPlayWithKids(hashedData.get("playWithKids").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setPassport(hashedData.get("passport").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setVaccinationCertificate(hashedData.get("vaccinationCertificate").toString().equalsIgnoreCase(String.valueOf(true)));
        }
        return entity;
    }
}