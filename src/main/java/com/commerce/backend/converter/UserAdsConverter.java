package com.commerce.backend.converter;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.constants.TrainningType;
import com.commerce.backend.dao.ItemCategoryRepository;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.dto.*;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.AdsFiltrationRequest;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;

import com.commerce.backend.model.request.userAds.UpdateAdRequest;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

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

    @PersistenceContext(type  =  PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

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
        	hashedData.put(d.get("id").toString().toLowerCase(), d.get("value"));
        }
     
        entity.setName(String.valueOf( getHashMapKeyWithCheck(hashedData,"name", -1)));
        entity.setCode(String.valueOf(hashedData.get("code")));
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
			((UserAccAds)entity).setCategory(category);
        }
        else if(request.getType() == AdsType.PET_CARE) {
            ((UserMedicalAds)entity).setAllowServiceAtHome((Boolean) hashedData.get("allow_at_home"));
            MedicalCategory medCategory = new MedicalCategory();
            medCategory.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(hashedData,"category", 0))));
            ((UserMedicalAds)entity).setCategory(medCategory);
        }
        else if(request.getType() == AdsType.SERVICE) {
			((UserServiceAds)entity).setAllowServiceAtHome(Boolean.parseBoolean(String.valueOf(getHashMapKeyWithCheck(hashedData,"allow_at_home", 1))));
			 this.loggerS.info("Passss allow at home");
			ServiceCategory serviceCategory = new ServiceCategory();
			serviceCategory.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(hashedData,"category", 0))));
			 ((UserServiceAds)entity).setCategory(serviceCategory);
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

    public UserAds updateAd(UpdateAdRequest<String, Object> request, UserAds ad) throws ParseException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        for(HashMap<String, Object> d: request.getUserAds()){
            data.put(d.get("id").toString().toLowerCase(), d.get("value"));
        }
        if(data.get("name") != null)
        {
            ad.setName(String.valueOf(data.get("name")));
        }
        if(data.get("code") != null)
        {
            ad.setCode(String.valueOf(data.get("code")));
        }
        if(data.get("active") != null)
        {
            ad.setActive(Boolean.parseBoolean(String.valueOf(data.get("active"))));
        }
        if(data.get("short_description") != null)
        {
            ad.setShortDescription(String.valueOf(data.get("short_description")));
        }
        if(data.get("description") != null)
        {
            ad.setDescription(String.valueOf(data.get("description")));
        }
        if(data.get("price") != null)
        {
            ad.setPrice(Float.parseFloat(String.valueOf(data.get("price"))));
        }
        ad.setUpdatedAt(new Date());
        if(data.get("longitude") != null && data.get("latitude") != null)
        {
            ad.setLongitude(Double.parseDouble(String.valueOf(getHashMapKeyWithCheck(data,"longitude", 0))));
            ad.setLatitude(Double.parseDouble(String.valueOf(getHashMapKeyWithCheck(data,"latitude", 0))));

            Coordinate coordinate = new Coordinate();

            coordinate.x = ad.getLongitude();
            coordinate.y = ad.getLatitude();

            String pointStr = String.format("POINT (%s %s)",ad.getLongitude(), ad.getLatitude());
            ad.setCoordinates(wktToGeometry(pointStr));
        }
        if(data.get("image") != null)
        {
            Object itemImage = getHashMapKeyWithCheck(data,"image", 2);
            if(itemImage != null) {
                if(itemImage instanceof ArrayList) {
                    List<?> imgList = (List<?>)itemImage;
                    Set<UserAdsImage> images = new HashSet<UserAdsImage>();
                    imgList.forEach(item -> {
                        UserAdsImage image = new UserAdsImage();
                        image.setImage(item.toString());
                        image.setIsExternalLink(ad.getExternalLink());
                        image.setUserAdsImage(ad);
                        images.add(image);
                        ad.getUserAdsImage().add(image);
                       });
                }
            }
        }
        if(ad.getType().equals(AdsType.PET_CARE))
        {
            if(data.get("allow_at_home") != null)
            {
                ((UserServiceAds) ad).setAllowServiceAtHome(Boolean.parseBoolean(String.valueOf(data.get("allow_at_home"))));
            }
            if(data.get("category") != null)
            {
                MedicalCategory medCategory = new MedicalCategory();
                medCategory.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(data,"category", 0))));
                ((UserMedicalAds)ad).setCategory(medCategory);
            }
        }
        else if(ad.getType().equals(AdsType.SERVICE))
        {
            if(data.get("allow_at_home") != null)
            {
                ((UserServiceAds) ad).setAllowServiceAtHome(Boolean.parseBoolean(String.valueOf(data.get("allow_at_home"))));
            }
            if(data.get("category") != null)
            {
                ServiceCategory serviceCategory = new ServiceCategory();
                serviceCategory.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(data,"category", 1))));
                ((UserServiceAds)ad).setCategory(serviceCategory);
            }
        }else if(ad.getType().equals(AdsType.ACCESSORIES))
        {
            if(data.get("used") != null)
            {
                ((UserAccAds)ad).setUsed(Boolean.parseBoolean(String.valueOf(data.get("used"))));
            }
            if(data.get("category") != null) {
                ItemCategory category = new ItemCategory();
                category.setId(Long.parseLong(String.valueOf(data.get("category"))));
                ((UserAccAds) ad).setCategory(category);
            }
        }else if(ad.getType().equals(AdsType.PETS))
        {
            if(data.get("category") != null)
            {
                PetCategory category = new PetCategory();
                category.setId(Long.parseLong(String.valueOf(getHashMapKeyWithCheck(data,"category", 0))));
                ((UserPetAds)ad).setCategory(category);
            }
            if(ad.getType() == AdsType.Dogs) {
                if(data.get("barkingProblem") != null)
                {
                    ((UserPetAds)ad).setBarkingProblem(Boolean.parseBoolean(String.valueOf(data.get("barkingProblem"))));
                }
            }
            if(data.get("breed") != null)
            {
                ((UserPetAds)ad).setBreed(String.valueOf(data.get("breed")));
            }
            if(data.get("weaned") != null )
            {
                ((UserPetAds)ad).setWeaned(Boolean.parseBoolean(String.valueOf(data.get("weaned"))));
            }
            if(data.get("neutering") != null)
            {
                ((UserPetAds)ad).setNeutering(Boolean.parseBoolean(String.valueOf(data.get("neutering"))));
            }
            if(data.get("vaccinationCertificate") != null)
            {
                ((UserPetAds)ad).setVaccinationCertifcate(Boolean.parseBoolean(String.valueOf(data.get("vaccinationCertificate"))));
            }
            if(data.get("passport") != null)
            {
                ((UserPetAds)ad).setPassport(Boolean.parseBoolean(String.valueOf(data.get("passport"))));
            }
            if(data.get("playWithKids") != null)
            {
                ((UserPetAds)ad).setPlayWithKids(Boolean.parseBoolean(String.valueOf(data.get("playWithKids"))));
            }
            if(data.get("diseasesDisabilities") != null)
            {
                ((UserPetAds)ad).setDiseasesDisabilities(Boolean.parseBoolean(String.valueOf(data.get("diseasesDisabilities"))));
            }
            if(data.get("diseasesDisabilitiesDesc") != null)
            {
                ((UserPetAds)ad).setDiseasesDisabilitiesDesc(String.valueOf(data.get("diseasesDisabilitiesDesc")));
            }
            if(data.get("training") != null)
            {
                ((UserPetAds)ad).setTraining(TrainningType.valueOf(String.valueOf(data.get("training"))));
            }
            if(data.get("food") != null)
            {
                ((UserPetAds)ad).setFood(String.valueOf(data.get("name")));
            }
            if(data.get("stock") != null)
            {
                ((UserPetAds)ad).setStock((Integer) data.get("stock"));
            }
        }
        return ad;
    }

    private  class Valid{
       public boolean success;
       public String msg;
    }

    public Query getQuery (AdsFiltrationRequest<String, Object> ads)
    {
        List<HashMap<String, Object>> filterRequest = ads.getUserAds();
        AdsType type =  ads.getType();
        HashMap<String, Object> data = new HashMap<String, Object>();
        
        String sql = "SELECT user_ads.*, ST_Distance(user_ads.geom, poi) / 1000 AS distance_km "
                + "            FROM user_ads user_ads, "
                + "            (SELECT ST_MakePoint(:long, :lat) as poi) as poi "
                + "            WHERE ST_DWithin(user_ads.geom, poi, :dist)";
        for(HashMap<String, Object> d: filterRequest){
        	data.put(d.get("id").toString().toLowerCase(), d.get("value"));
        }
        if(type != null) {
        	sql += " AND type = :type ";
        }
        if(data.get("category") != null) {
            sql += " AND category_id = :cat ";
        }
        if(data.get("name") != null)
        {
            sql += " AND name LIKE :name ";
        }
     
        if(data.get("active") != null)
        {
            sql += " AND active = :active ";//+variable++;
        }
        if(data.get("allow_at_home") != null && data.get("allow_at_home").equals(true))
        {
            sql += " AND allow_at_home = true ";
        }
       
        if(data.get("short_description") != null)
        {
            sql += " AND short_description LIKE :short_description ";
        }
        
        if(data.get("price") != null)
        {
            sql += " AND NULLIF(price, 0) BETWEEN  :from AND :to ";
        }

        if(type != null && type.getType().equals("ACCESSORIES"))
        {
            if(data.get("used") != null && data.get("used").equals(true))
            {
                sql += " AND used = true";
            }
        }

        if(type != null && type.getType().equals("PETS"))
        {
            if(data.get("breed") != null)
            {
                sql += " AND breed LIKE :breed ";
            }
            if(data.get("weaned") != null )
            {
                sql += " AND weaned = :weaned ";
            }
            if(data.get("neutering") != null)
            {
                sql += " AND neutering = :neutering ";
            }
            if(data.get("vaccinationcertificate") != null)
            {
                sql += " AND vaccination_certificate = :vC ";
            }
            if(data.get("passport") != null)
            {
                sql += " AND passport = :passport ";
            }
            if(data.get("playwithkids") != null)
            {
                sql += " AND play_with_kids = :playWithKids ";
            }
            if(data.get("diseasesdisabilities") != null)
            {
                sql += " AND diseases_disabilities = :diseasesDisabilities ";
            }
            if(data.get("barkingproblem") != null)
            {
                sql += " AND barking_problem = :barkingproblem ";
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
        Double distance = Double.parseDouble(this.getHashMapKeyWithCheck(data, "distance", 0).toString()) ;
        distance =  distance == 0 ? SystemConstant.DISTANCE: distance;
        Query query = entityManager.createNativeQuery(sql, UserAds.class)              
                .setParameter("long", Double.parseDouble(this.getHashMapKeyWithCheck(data, "longitude", 0).toString()))
                .setParameter("lat",  Double.parseDouble(this.getHashMapKeyWithCheck(data, "latitude", 0).toString()))
                .setParameter("dist", distance);
      
       System.out.println("--------------------");
        if(type != null) {
        	System.out.println(type);
        	query.setParameter("type", type.getType());
        }
        if(data.get("category") != null) {
            query.setParameter("cat", Long.parseLong(this.getHashMapKeyWithCheck(data, "category", 0).toString() ));
        }
        if(data.get("name") != null)
        {
            query.setParameter("name", data.get("name"));
        }
        
        if(data.get("active") != null)
        {
        	 query.setParameter("active", Boolean.parseBoolean(String.valueOf(data.get("active"))));
        }
        if(data.get("description") != null)
        {
            query.setParameter("description", data.get("description"));
        }
        if(data.get("short_description") != null)
        {
            query.setParameter("short_description", data.get("short_description"));
        }
        if(data.get("breed") != null)
        {
            query.setParameter("breed", data.get("breed"));
        }
        if(data.get("training") != null)
        {
            query.setParameter("training", data.get("training"));
        }
        if(data.get("food") != null)
        {
            query.setParameter("food", data.get("food"));
        }
        if(data.get("weaned") != null)
        {
            query.setParameter("weaned", Boolean.parseBoolean(String.valueOf(data.get("weaned"))));
        }
        if(data.get("neutering") != null)
        {
            query.setParameter("neutering",Boolean.parseBoolean(String.valueOf( data.get("neutering"))));
        }
        if(data.get("vaccinationcertificate") != null)
        {
            query.setParameter("vC", Boolean.parseBoolean(String.valueOf(data.get("vaccinationcertificate"))));
        }
        if(data.get("passport") != null)
        {
            query.setParameter("passport", Boolean.parseBoolean(String.valueOf(data.get("passport"))));
        }
        if(data.get("playwithkids") != null)
        {
            query.setParameter("playWithKids", Boolean.parseBoolean(String.valueOf(data.get("playwithkids"))));
        }
        if(data.get("diseasesdisabilities") != null)
        {
            query.setParameter("diseasesDisabilities", Boolean.parseBoolean(String.valueOf(data.get("diseasesdisabilities"))));
        }
        if(data.get("barkingproblem") != null)
        {
            query.setParameter("barkingproblem", Boolean.parseBoolean(String.valueOf(data.get("barkingproblem"))));
        }
        if(data.get("price") != null)
        {
            Double prFrom = 0d; //Double.parseDouble(String.valueOf(data.get("price"))) / 2;
            Double prTo = Double.parseDouble(String.valueOf(data.get("price"))) + 1;
            query.setParameter("from", prFrom);
            query.setParameter("to", prTo);
        }
        return query;
    }
    
    public Geometry wktToGeometry(String wellKnownText) throws ParseException {
    		    return new WKTReader().read(wellKnownText);
    }
    
}
