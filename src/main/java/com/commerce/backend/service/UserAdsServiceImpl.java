package com.commerce.backend.service;


import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.UserAdsConverter;
import com.commerce.backend.converter.UserAdsToVoConverter;
import com.commerce.backend.dao.*;
import com.commerce.backend.model.dto.*;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;
import com.commerce.backend.model.request.userAds.LocationRequest;
import com.commerce.backend.model.request.userAds.UserPetsAdsRequest;
import com.commerce.backend.model.request.userAds.adTypeRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.xml.stream.Location;

import static java.lang.System.currentTimeMillis;

@Service
public class UserAdsServiceImpl implements UserAdsService {
	private UserPetsAdsRepository userPetsAdsRepository;
	private UserServiceAdsRepository userServiceAdsRepository;
	private UserItemsAdsRepository userItemsAdsRepository;
	private UserMedicalAdsRepository userMedicalAdsRepository;
	
	private UserAdsToVoConverter userAdsToVoConverter;
	private UserAdsConverter userAdsConverter;
	private UserAdsImageRepository userAdsImageRepository;
	private CustomUserAdsRepo repo;
	private CustomUserAdsCriteriaHelper customUserAdsCriteriaHelper;
	private final Path rootLocation = Paths.get("upload");
	@Value("${swagger.host.path}")
	private String path;
	private static final Logger loggerS = LoggerFactory.getLogger(UserAdsServiceImpl.class);
	@Autowired
	public UserAdsServiceImpl(UserAdsRepository userAdsRepository, UserPetsAdsRepository userPetsAdsRepository,
							  UserServiceAdsRepository userServiceAdsRepository,
							  UserItemsAdsRepository userItemsAdsRepository, UserMedicalAdsRepository userMedicalAdsRepository,
							  UserAdsToVoConverter userAdsToVoConverter, UserAdsConverter userAdsConverter,
							  UserAdsImageRepository userAdsImageRepository,
							  CustomUserAdsRepo repo,
							  CustomUserAdsCriteriaHelper customUserAdsCriteriaHelper) {
	

	
		
		this.userPetsAdsRepository = userPetsAdsRepository;
		this.userServiceAdsRepository = userServiceAdsRepository;
		this.userItemsAdsRepository = userItemsAdsRepository;
		this.userMedicalAdsRepository = userMedicalAdsRepository;
		this.userAdsToVoConverter = userAdsToVoConverter;
		this.userAdsConverter = userAdsConverter;
		this.userAdsImageRepository = userAdsImageRepository;
		this.repo = repo;
		this.customUserAdsCriteriaHelper = customUserAdsCriteriaHelper;
	}

	public UserAdsServiceImpl() {

	}

	@Override
	public ProductDetailsResponse findByUrl(String url) {
		return null;
	}

	@Override
	public BasicResponse getAll(AdsType type, LocationRequest location, Integer page, Integer size, String sort, Long category, Float minPrice,
								Float maxPrice) {
		try {
/* <<<<<<< complete_form_bulider
		Pageable pageable = PageRequest.of(page, size);
		 BasicResponse response = new BasicResponse();
		 HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		if(type == AdsType.ACCESSORIES) {
			Page<UserAccAds> userAccAds = this.userItemsAdsRepository.findAll(pageable);
			 response.setMsg("success");
			 response.setSuccess(true);
			 hashMap.put("count", userAccAds.getSize());
			 hashMap.put("data", userAccAds);
		}
		else if(type == AdsType.PET_CARE) {
		    Page<UserMedicalAds> userMedicalAds = this.userMedicalAdsRepository.findAll(pageable);
		     hashMap.put("count", userMedicalAds.getSize());
			 hashMap.put("data", userMedicalAds);
		}
		else if(type == AdsType.PETS) {
			Page<UserPetAds> userPetAds = this.userPetsAdsRepository.findAll(pageable);
			
			hashMap.put("count", userPetAds.getSize());
			 hashMap.put("data", userPetAds);
		} 
		else if(type == AdsType.SERVICE) {
		    List<UserServiceAds> userServiceAds =  this.userServiceAdsRepository.findAllMobile();
		    
		    List<UserAdsVO> userAds =   userServiceAds.stream()
		    .map(userAdsToVoConverter)
		    .collect(Collectors.toList());
		    hashMap.put("count", userServiceAds.size());				    
			hashMap.put("data", userAds );
		}
		 response.setResponse(hashMap);
		 return response;
=======*/
			Pageable pageable = PageRequest.of(page, size);
			List<UserAdsVO> collect = new ArrayList<>();
			if(location != null)
			{
				Page<UserAds> ads = repo.findAll(location.getLongitude(), location.getLatitude(), type.getType(), pageable);
				ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
			}else if(type != null){
				Page<UserAds> ads = repo.findUserAdsByType(type.getType(), pageable);
				ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
			}
			return res(collect, true);
		 
		}catch(Exception ex) {
			
			 BasicResponse response = new BasicResponse();
			 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			 hashMap.put("success",false);
			 hashMap.put("error", ex.getMessage());
			 response.setResponse(hashMap);
			 return response;
		}
	}

	@Override
	public Long getAllCount(UserAdsVO userAdsVO, Float minPrice, Float maxPrice) {
		return 0L;
	}

	@Override
	public List<UserAdsVO> getRelatedAds(UserAdsVO userAd) {
	//	List<UserAds> userAds = this.userAdsRepository.getReleatedAds(userAd);
		return null;
	}

	@Override
	public List<UserAdsVO> getNewlyAddedAds(AdsType adsType, Long Category) {
	//	this.userAdsRepository.getNewlyAddedAds(adsType, Category);
		return null;
	}

	@Deprecated
	@Override
	public List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, UserAdsVO adsCriteria) {
	//	List<UserAds> userAds = this.userAdsRepository.getNearByAds(page, size, sort, category, minPrice, maxPrice, adsCriteria);
		return null;
	}

	@Override
	public BasicResponse findNearby(double longitude, double latitude, Integer distance, Integer page, Integer size, Long category)
	{
		try {
			
		Pageable pageable = PageRequest.of(page, size);
		List<UserAds> ads = customUserAdsCriteriaHelper.findNearestByCategory(longitude, latitude, distance, pageable, category);
		List<UserAdsVO> collect = new ArrayList<>();
		ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
		return res(collect, true);
		}catch(Exception ex) {
			
			return res(ex, false);
		}
	}
	@Deprecated
	@Override
	public List<UserAdsVO> getNearByAdsByCategory(AdsType adsType, Long Category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse findAdsById(Long id) {
		try{
			UserAds ad = (UserAds) repo.findById(id).orElse(null);
			UserAdsVO vo = userAdsToVoConverter.apply(ad);
			return res(vo, true);
		}
		catch (Exception e)
		{
			return res(e, false);
		}
	}

	public BasicResponse res(Object obj, boolean sucess )
	{
		BasicResponse res = new BasicResponse();
		HashMap<String, Object> map = new HashMap<>();

		if( obj instanceof Exception) {
		 map.put(MessageType.Data.getMessage(), ((Exception) obj).getMessage());
		} else {
			map.put(MessageType.Data.getMessage(),  obj);
		}
		res.setSuccess(sucess);
		res.setResponse(map);
		return res;
	}


	@Override
	public List<UserAdsVO> searchItemDisplay(String keyword, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAdsVO> getInterested(Long userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}
    @Transactional(propagation = Propagation.REQUIRED)
	@Override
	public BasicResponse createUserAds(DynamicAdsRequest<String, Object> ads, List<String> files, List<MultipartFile> file, boolean external) {
		BasicResponse response = new BasicResponse();
		try {
		UserAds entity = this.userAdsConverter.convertRequestToEntity(ads);
		this.loggerS.info("external", external);
		entity.setExternalLink(external);
		this.saveEntityFiles(entity, files, file, ads.getType(), external);
		if(ads.getType() == AdsType.ACCESSORIES) 
		{
			this.userItemsAdsRepository.save((UserAccAds)entity);
		}
		else if(ads.getType() == AdsType.PET_CARE) {
			this.userMedicalAdsRepository.save((UserMedicalAds)entity);
		}
		else if(ads.getType() == AdsType.PETS || ads.getType() == AdsType.Dogs) {
			//((UserPetAds) entity).setImage("upload/"+ ads.getType() + file.get(0).getName());	
			
	    	this.userPetsAdsRepository.save((UserPetAds)entity);
		}
		else if(ads.getType() == AdsType.SERVICE) {
		   this.userServiceAdsRepository.save((UserServiceAds)entity);
		} else if(ads.getType() == AdsType.DRIVER) {
			
		}
		
		response.setSuccess(true);
		response.setMsg("Ads created successfully ");
		HashMap<String ,Object> map = new HashMap<String, Object>();
		UserAdsVO userAdVo = this.userAdsToVoConverter.apply(entity);
		map.put("data", userAdVo);
		map.put("id", entity.getId());
		response.setResponse(map);
		return response;
	}catch(Exception ex) {
		this.loggerS.info("errr", ex.getMessage());
		ex.printStackTrace();
		response.setMsg(ex.getMessage());
		
	}
	return response;
	}

	@Override
	public Long getAllCount(String category, Float minPrice, Float maxPrice, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse getCreateForm(adTypeRequest adsType){

		InputStream is = null;
		BasicResponse response = new BasicResponse();
		HashMap<String, Object> mapResponse = new HashMap<String, Object>(); 
		try {
		is = new ClassPathResource("jsonFiles/basicResponse.json").getInputStream();
		if(adsType != null)
		{
			String adType = adsType.getAdsType().getType().toLowerCase();
			is = new ClassPathResource("jsonFiles/"+adType+"Rs.json").getInputStream();
		}
       
		
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(
					new InputStreamReader(is, StandardCharsets.UTF_8));
			response.setMsg(MessageType.Success.getMessage());
			response.setSuccess(true);
            mapResponse.put(MessageType.Data.getMessage(), jsonObject);
			response.setResponse(mapResponse);
			
		} catch (Exception e) {
			response.setMsg(e.getMessage());
			response.setSuccess(false);
		} finally {
			if(is != null) {
			  try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.setMsg(e.getMessage());
				response.setSuccess(false);
			
			}
			}
		}
		return response;
	}
	
	@Override
	public BasicResponse getFilterForm(adTypeRequest adsType){

		InputStream is = null;
		BasicResponse response = new BasicResponse();
		HashMap<String, Object> mapResponse = new HashMap<String, Object>(); 
		try {
		is = new ClassPathResource("jsonFilter/basicResponse.json").getInputStream();
		if(adsType != null)
		{
			String adType = adsType.getAdsType().getType().toLowerCase();
			is = new ClassPathResource("jsonFilter/"+adType+"Rs.json").getInputStream();
		}
       
		
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(
					new InputStreamReader(is, StandardCharsets.UTF_8));
			response.setMsg(MessageType.Success.getMessage());
			response.setSuccess(true);
            mapResponse.put(MessageType.Data.getMessage(), jsonObject);
			response.setResponse(mapResponse);
			
		} catch (Exception e) {
			response.setMsg(e.getMessage());
			response.setSuccess(false);
		} finally {
			if(is != null) {
			  try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.setMsg(e.getMessage());
				response.setSuccess(false);
			
			}
			}
		}
		return response;
	}

	@Override
	public <T> UserAdsVO savePet(UserPetsAdsRequest userPetsAdsRequest) {
		return new UserPetAdsVO();
	}
	
	public boolean saveEntityFiles(UserAds entity, List<String> fileList, List<MultipartFile> files, AdsType type, boolean external) {
		try {	
		if(files != null && !external) {
			String filename = currentTimeMillis() + "-" + StringUtils.cleanPath(Objects.requireNonNull(files.get(0).getOriginalFilename()));
			filename = filename.toLowerCase().replaceAll(" ", "-");
			try {
				Files.copy(files.get(0).getInputStream(), rootLocation.resolve(filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
			((UserPetAds) entity).setImage("/upload/ads/" + type.getType()+ "/" + filename);
			if(files.size() > 1)
			{
				UserAdsImage userAdsImage = new UserAdsImage();
				for (int i = 1; i < files.size(); i++)
				{
					userAdsImage.setImage("/upload/ads/" + type.getType()+ "/"  + filename);
					userAdsImage.setUserAdsImage((UserPetAds) entity);
					userAdsImageRepository.save(userAdsImage);
				}

			}
		}else if(external && fileList != null) {
			
			fileList.stream().forEach(path -> {
				UserAdsImage userAdsImage = new UserAdsImage();
				userAdsImage.setImage(path);
				userAdsImage.setIsExternalLink(true);
				userAdsImage.setUserAdsImage(entity);
				userAdsImageRepository.save(userAdsImage);
			});
		}
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}


}
