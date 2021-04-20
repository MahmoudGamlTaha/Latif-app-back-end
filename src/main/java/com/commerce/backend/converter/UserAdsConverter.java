package com.commerce.backend.converter;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.FoodType;
import com.commerce.backend.constants.TrainningType;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.dto.*;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.AdsFiltrationRequest;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;
import com.commerce.backend.service.UserAdsServiceImpl;

import org.checkerframework.common.value.qual.ArrayLen;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public class UserAdsConverter implements Function<UserAds, UserAdsVO> {

    @PersistenceContext(type  =  PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

	@Autowired
	private UserRepository user;
    @Override
    public UserAdsVO apply(UserAds userAds) {
        return null;
    }
    private static final Logger loggerS = LoggerFactory.getLogger(UserAdsConverter.class);
    public UserAds convertRequestToEntity(DynamicAdsRequest<String, Object> request)
    {
        UserAds entity = null;
        if(request.getType() == AdsType.ACCESSORIES) {
            entity = new UserAccAds();
        }
        else if(request.getType() == AdsType.PET_CARE){
            entity = new UserMedicalAds();
        }
        else if(request.getType() == AdsType.PETS || request.getType() == AdsType.Dogs) {
            entity = new UserPetAds();
        }
        else if(request.getType() == AdsType.SERVICE) {
        	
            entity = new UserServiceAds();
        }
        return convertToEntity(request, entity);
    }
    /*
     * url option link should return name, nameAr, id at least
     * 
     */
    private UserAds convertToEntity(DynamicAdsRequest<String, Object> request, UserAds entity) {
        List<HashMap<String, Object>> data = request.getUserAds();
       
        HashMap<String, Object> hashedData = new HashMap<String, Object>();
        for(HashMap<String, Object> d: data){
        	this.loggerS.info(String.valueOf(d.get("value")));
        	this.loggerS.info("XXXXXXXXXXXXXXXXXXX");
        	hashedData.put(d.get("id").toString().toLowerCase(), d.get("value"));
        }
     
        entity.setName(String.valueOf( getHashMapKeyWithCheck(hashedData,"name", -1)));
        entity.setDescription(String.valueOf( getHashMapKeyWithCheck(hashedData,"description", -1)));
        entity.setShortDescription(String.valueOf( getHashMapKeyWithCheck(hashedData,"short_description", -1)));
        entity.setActive(Boolean.parseBoolean(String.valueOf(getHashMapKeyWithCheck(hashedData,"active", 1))));
        entity.setType(request.getType());
        entity.setPrice(Float.parseFloat(String.valueOf(getHashMapKeyWithCheck(hashedData,"price", 0))));
        entity.setLongitude(Double.parseDouble(String.valueOf(getHashMapKeyWithCheck(hashedData,"longitude", 0))));
        entity.setLatitude(Double.parseDouble(String.valueOf(getHashMapKeyWithCheck(hashedData,"latitude", 0))));
        entity.setCreatedAt((new Date()));
        entity.setUpdatedAt((new Date()));
        Object itemImage = getHashMapKeyWithCheck(hashedData,"image", 2);
        if(itemImage != null) {
        	if(itemImage instanceof ArrayList) {
        	   List<?> imgList = (List<?>)itemImage;
        		Set<UserAdsImage> images = new HashSet<UserAdsImage>();
        		imgList.forEach(item -> {
        			UserAdsImage image = new UserAdsImage(); 	
        			image.setImage(item.toString());
        			image.setIsExternalLink(entity.getExternalLink());
        		    image.setUserAdsImage(entity);
        			images.add(image);
        			entity.getUserAdsImage().add(image);
        			 this.loggerS.info("image added path :"+ item.toString() );
        		
        		});
        		
        	}
        }
        
        User user = new User();
        String userId =  String.valueOf(getHashMapKeyWithCheck(hashedData,"created_by", 0));
        userId = userId.equals("0")? "1" : userId;
        user.setId(Long.parseLong(userId));
        
        entity.setCreatedBy(user);

        if(request.getType() == AdsType.PETS || request.getType() == AdsType.Dogs)
        {
            PetCategory category = new PetCategory();
            category.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(hashedData,"category", 0))));
     
            if(request.getType() == AdsType.Dogs) {
            ((UserPetAds)entity).setBarkingProblem(hashedData.get("barkingproblem").toString().equalsIgnoreCase(String.valueOf(true)));
            }
            ((UserPetAds)entity).setBreed(String.valueOf(getHashMapKeyWithCheck(hashedData, "breed", -1)));
            ((UserPetAds)entity).setStock(Integer.parseInt(String.valueOf(getHashMapKeyWithCheck(hashedData,"stock", 0))));
            ((UserPetAds)entity).setWeaned(Boolean.parseBoolean(String.valueOf(getHashMapKeyWithCheck(hashedData,"weaned", 1))));
            ((UserPetAds)entity).setFood(String.valueOf(getHashMapKeyWithCheck(hashedData,"food", -1)));
            ((UserPetAds)entity).setDiseasesDisabilities(hashedData.get("diseasesdisabilities").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setDiseasesDisabilitiesDesc((String) hashedData.get("diseasesdisabilitiesdesc"));
            ((UserPetAds)entity).setNeutering(hashedData.get("neutering").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setTraining(TrainningType.valueOf( (String) hashedData.get("training")));
            ((UserPetAds)entity).setPlayWithKids(hashedData.get("playwithkids").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setPassport(hashedData.get("passport").toString().equalsIgnoreCase(String.valueOf(true)));
            ((UserPetAds)entity).setVaccinationCertifcate(hashedData.get("vaccinationcertificate").toString().equalsIgnoreCase(String.valueOf(true)));
        }
       
        if(request.getType() == AdsType.ACCESSORIES) {
            assert entity instanceof UserAccAds;
            ((UserAccAds)entity).setUsed((Boolean) hashedData.get("used"));
			ItemCategory category = new ItemCategory();
			category.setId(Long.parseLong(String.valueOf(hashedData.get("category"))));
			//((UserAccAds)entity).setItemCategoryId(category);
        }
        else if(request.getType() == AdsType.PET_CARE) {
            ((UserMedicalAds)entity).setAllowServiceAtHome((Boolean) hashedData.get("allow_at_home"));
        }
        else if(request.getType() == AdsType.SERVICE) {
			((UserServiceAds)entity).setAllowServiceAtHome(Boolean.parseBoolean(String.valueOf(getHashMapKeyWithCheck(hashedData,"allow_at_home", 1))));
			 this.loggerS.info("Passss allow at home");
			ServiceCategory serviceCategory = new ServiceCategory();
			serviceCategory.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(hashedData,"category", 1))));
			 ((UserServiceAds)entity).setServiceCategory(serviceCategory);
			 this.loggerS.info("Passss create");
        }
        return entity;
    }
    
    @SuppressWarnings("unused")
	private Valid validateHashMap(HashMap<String, Object> hashedData, AdsType type) {
    	return new Valid();
    }
    private Object getHashMapKeyWithCheck(HashMap<String, Object> hashedData, String key, int typeId) {
    	if(hashedData.containsKey(key)) {
    		return hashedData.get(key);
    	}
    if(typeId == 0)  // integer
    	return 0;
    if(typeId == 1)  // boolean
        return false;
    if(typeId == 2)
    	return null;
    return "N/A";
    }
    private  class Valid{
       public boolean success;
       public String msg;
    }

    public Query getQuery (AdsFiltrationRequest<String, Object> ads)
    {
        HashMap<String, Object> data = ads.getUserAds().get(0);
        String sql = "SELECT user_ads.*, ST_Distance(user_ads.geom, poi) / 1000 AS distance_km "
                + "            FROM user_ads user_ads, "
                + "            (select ST_MakePoint(:longitude, :latitude) as poi) as poi "
                + "            WHERE ST_DWithin(user_ads.geom, poi, :distance) AND type = :type";

        if(data.get("category") != null) {
            sql += " AND category_id = :category ";
        }
        if(data.get("name") != null)
        {
            sql += " AND name LIKE :name ";
        }
        if(data.get("code") != null)
        {
            sql += " AND code = :code ";
        }
        if(data.get("active") != null && data.get("active").equals(true))
        {
            sql += " AND active = true ";
        }
        if(data.get("allow_at_home") != null && data.get("allow_at_home").equals(true))
        {
            sql += " AND allow_at_home = true ";
        }
        if(data.get("description") != null)
        {
            sql += " AND description LIKE :description ";
        }
        if(data.get("short_description") != null)
        {
            sql += " AND short_description LIKE :short_description ";
        }
        if(data.get("price") != null)
        {
            sql += " AND price BETWEEN  :from AND :to";
        }

        if(ads.getType().getType().equals("ACCESSORIES"))
        {
            if(data.get("used") != null && data.get("used").equals(true))
            {
                sql += " AND used = true";
            }
        }

        if(ads.getType().getType().equals("PETS"))
        {
            if(data.get("breed") != null)
            {
                sql += " AND breed LIKE :breed ";
            }
            if(data.get("weaned") != null && data.get("weaned").equals(true))
            {
                sql += " AND weaned = true";
            }
            if(data.get("neutering") != null && data.get("neutering").equals(true))
            {
                sql += " AND weaned = true";
            }
            if(data.get("vaccinationCertificate") != null && data.get("vaccinationCertificate").equals(true))
            {
                sql += " AND vaccination_certificate = true";
            }
            if(data.get("passport") != null && data.get("passport").equals(true))
            {
                sql += " AND passport = true";
            }
            if(data.get("playWithKids") != null && data.get("playWithKids").equals(true))
            {
                sql += " AND play_with_kids = true";
            }
            if(data.get("diseasesDisabilities") != null && data.get("diseasesDisabilities").equals(true))
            {
                sql += " AND diseases_disabilities = true";
            }
            if(data.get("barkingProblem") != null && data.get("barkingProblem").equals(true))
            {
                sql += " AND barking_problem = true";
            }
            if(data.get("training") != null)
            {
                sql += " AND training LIKE :training ";
            }
            if(data.get("food") != null)
            {
                sql += " AND food LIKE :food ";
            }
        }

        sql += " ORDER BY ST_Distance(geom, poi) ";
        Query query = entityManager.createNativeQuery(sql, UserAds.class)
                .setParameter("longitude", data.get("longitude"))
                .setParameter("latitude", data.get("latitude"))
                .setParameter("distance", data.get("distance"))
                .setParameter("type", ads.getType().getType());

        if(data.get("category") != null) {
            query.setParameter("category", data.get("category"));
        }
        if(data.get("name") != null)
        {
            query.setParameter("name", "%"+data.get("name")+"%");
        }
        if(data.get("code") != null)
        {
            query.setParameter("code", data.get("code"));
        }
        if(data.get("description") != null)
        {
            query.setParameter("description", "%"+data.get("description")+"%");
        }
        if(data.get("short_description") != null)
        {
            query.setParameter("short_description", "%"+data.get("short_description")+"%");
        }
        if(data.get("breed") != null)
        {
            query.setParameter("breed", "%"+data.get("breed")+"%");
        }
        if(data.get("training") != null)
        {
            query.setParameter("training", "%"+data.get("training")+"%");
        }
        if(data.get("food") != null)
        {
            query.setParameter("food", "%"+data.get("food")+"%");
        }
        if(data.get("price") != null)
        {
            Integer prFrom = (Integer) data.get("price") / 2;
            Integer prTo = (Integer) data.get("price") / 2 + (Integer) data.get("price");
            query.setParameter("from", prFrom);
            query.setParameter("to", prTo);
        }
        return query;
    }
    
}
