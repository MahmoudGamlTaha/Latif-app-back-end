package com.commerce.backend.converter;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.FoodType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.constants.TrainningType;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;

import org.json.JSONArray;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import com.vividsolutions.jts.geom.PrecisionModel;

@Component
public class UserAdsConverter implements Function<UserAds, UserAdsVO> {
   
	@Autowired
	private UserRepository user;
	
	private final static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SystemConstant.COORDINATE_SYSTEM); 
    @Override
    public UserAdsVO apply(UserAds userAds) {
        return null;
    }
    private static final Logger loggerS = LoggerFactory.getLogger(UserAdsConverter.class);
    public UserAds convertRequestToEntity(DynamicAdsRequest<String, Object> request)  throws ParseException
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
    private UserAds convertToEntity(DynamicAdsRequest<String, Object> request, UserAds entity)  throws ParseException {
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
        //entity.setUpdatedAt((new Date()));
        
        Coordinate coordinate = new Coordinate();
        
        coordinate.x = entity.getLongitude();
        coordinate.y = entity.getLatitude();
        
        String pointStr = String.format("POINT (%s %s)",entity.getLongitude(), entity.getLatitude());
        entity.setCoordinates(wktToGeometry(pointStr));
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
            ((UserPetAds)entity).setCategory(category);
        }
       
        if(request.getType() == AdsType.ACCESSORIES) {
            assert entity instanceof UserAccAds;
            ((UserAccAds)entity).setUsed((Boolean) hashedData.get("used"));
			ItemCategory category = new ItemCategory();
			category.setId(Long.parseLong(String.valueOf(hashedData.get("category"))));
			((UserAccAds)entity).setItemCategoryId(category);
        }
        else if(request.getType() == AdsType.PET_CARE) {
            ((UserMedicalAds)entity).setAllowServiceAtHome((Boolean) hashedData.get("allow_at_home"));
            MedicalCategory medCategory = new MedicalCategory();
            medCategory.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(hashedData,"category", 0))));
            ((UserMedicalAds)entity).setMedicalCategoryType(medCategory);
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
    
    public Geometry wktToGeometry(String wellKnownText) throws ParseException {
    		    return new WKTReader().read(wellKnownText);
    		}
    
}
