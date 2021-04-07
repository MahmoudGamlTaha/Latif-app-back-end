package com.commerce.backend.service;


import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.UserAdsConverter;
import com.commerce.backend.converter.UserAdsToVoConverter;
import com.commerce.backend.dao.*;
import com.commerce.backend.model.dto.*;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


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
	private final Path rootLocation = Paths.get("upload");
	@Value("${swagger.host.path}")
	private String path;
	private static final Logger loggerS = LoggerFactory.getLogger(UserAdsServiceImpl.class);
	@Autowired
	public UserAdsServiceImpl(UserAdsRepository userAdsRepository, UserPetsAdsRepository userPetsAdsRepository,
							  UserServiceAdsRepository userServiceAdsRepository,
							  UserItemsAdsRepository userItemsAdsRepository, UserMedicalAdsRepository userMedicalAdsRepository,
							  UserAdsToVoConverter userAdsToVoConverter, UserAdsConverter userAdsConverter, UserAdsImageRepository userAdsImageRepository, CustomUserAdsRepo repo) {
	

	
		
		this.userPetsAdsRepository = userPetsAdsRepository;
		this.userServiceAdsRepository = userServiceAdsRepository;
		this.userItemsAdsRepository = userItemsAdsRepository;
		this.userMedicalAdsRepository = userMedicalAdsRepository;
		this.userAdsToVoConverter = userAdsToVoConverter;
		this.userAdsConverter = userAdsConverter;
		this.userAdsImageRepository = userAdsImageRepository;
		this.repo = repo;
	}

	public UserAdsServiceImpl() {

	}

	@Override
	public ProductDetailsResponse findByUrl(String url) {
		return null;
	}

	@Override
	public BasicResponse getAll(AdsType type, Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice) {
		try {
		Pageable pageable = PageRequest.of(page, size);
		 BasicResponse response = new BasicResponse();
		 HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		if(type == AdsType.ACCESSORIES) {
			Page<UserAccAds> userAccAds = this.userItemsAdsRepository.findAll(pageable);
			  /* userAccAds.get()
			   .map(this.userAdsConverter)
			   .collect(Collectors.toList());*/
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
		 
		}catch(Exception ex) {
			
			 BasicResponse response = new BasicResponse();
			 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			 hashMap.put("success",false);
			 hashMap.put("error", ex.getMessage());
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

	@Override
	public List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, UserAdsVO adsCriteria) {
	//	List<UserAds> userAds = this.userAdsRepository.getNearByAds(page, size, sort, category, minPrice, maxPrice, adsCriteria);
		return null;
	}

	@Override
	public BasicResponse findNearest(double longitude, double latitude, Pageable page)
	{
		List<UserAds> ads = repo.findNearest(longitude, latitude, page);
		List<UserAdsVO> collect = new ArrayList<>();
		ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
		return res(collect);
	}
	@Override
	public List<UserAdsVO> getNearByAdsByCategory(AdsType adsType, Long Category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse findAdsById(Long id) throws Exception {
		try{
			UserAds ad = (UserAds) repo.findById(id).orElse(null);
			UserAdsVO vo = userAdsToVoConverter.apply(ad);
			return res(vo);
		}
		catch (Exception e)
		{
			throw new Exception("Error: "+e);
		}
	}

	public BasicResponse res(Object obj)
	{
		BasicResponse res = new BasicResponse();
		HashMap<String, Object> map = new HashMap<>();
		map.put("data", obj);
		res.setSuccess(true);
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

	@Override
	public BasicResponse createUserAds(DynamicAdsRequest<String, String> ads, List<MultipartFile> file) {
		UserAds entity = this.userAdsConverter.convertRequestToEntity(ads);
		if(ads.getType() == AdsType.ACCESSORIES) {

			this.userItemsAdsRepository.save((UserAccAds)entity);
		}
		else if(ads.getType() == AdsType.PET_CARE) {
			this.userMedicalAdsRepository.save((UserMedicalAds)entity);
		}
		else if(ads.getType() == AdsType.PETS) {
			if(file != null) {
				String filename = currentTimeMillis() + "-" + StringUtils.cleanPath(Objects.requireNonNull(file.get(0).getOriginalFilename()));
				filename = filename.toLowerCase().replaceAll(" ", "-");
				try {
					Files.copy(file.get(0).getInputStream(), rootLocation.resolve(filename));
				} catch (Exception e) {
					e.printStackTrace();
				}
				((UserPetAds) entity).setImage("upload/pets" + filename);
				if(file.size() > 1)
				{
					UserAdsImage userAdsImage = new UserAdsImage();
					for (int i = 1; i < file.size(); i++)
					{
						userAdsImage.setImage("upload/pets" + filename);
						userAdsImage.setUserAdsImage((UserPetAds) entity);
						userAdsImageRepository.save(userAdsImage);
					}

				}
			}
			this.userPetsAdsRepository.save((UserPetAds)entity);
		}
		else if(ads.getType() == AdsType.SERVICE) {
		   this.userServiceAdsRepository.save((UserServiceAds)entity);
		}
		BasicResponse response = new BasicResponse();
		response.setSuccess(true);
		response.setMsg("Ads created successfully ");
		HashMap<String ,Object> map = new HashMap<String, Object>();
		map.put("data", entity);
		map.put("id", entity.getId());
		response.setResponse(map);
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
	public <T> UserAdsVO savePet(UserPetsAdsRequest userPetsAdsRequest) {
		return new UserPetAdsVO();
	}


}
