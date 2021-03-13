package com.commerce.backend.service;


import com.commerce.backend.constants.AdsType;
import com.commerce.backend.converter.UserAdsToVoConverter;
import com.commerce.backend.converter.UserServiceAdsToVoConverter;
import com.commerce.backend.dao.UserItemsAdsRepository;
import com.commerce.backend.dao.UserMedicalAdsRepository;
import com.commerce.backend.dao.UserPetsAdsRepository;
import com.commerce.backend.dao.UserServiceAdsRepository;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserMedicalAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;
import com.commerce.backend.model.request.userAds.UserAdsGeneralAdsRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class UserAdsServiceImpl implements UserAdsService {
	private UserPetsAdsRepository userPetsAdsRepository;
	private UserServiceAdsRepository userServiceAdsRepository;
	private UserItemsAdsRepository userItemsAdsRepository;
	private UserMedicalAdsRepository userMedicalAdsRepository;
	private UserAdsToVoConverter userAdsToVoConverter;
	private static final Logger loggerS = LoggerFactory.getLogger(UserAdsServiceImpl.class);
	@Autowired
	public UserAdsServiceImpl(UserPetsAdsRepository userPetsAdsRepository,
			UserServiceAdsRepository userServiceAdsRepository, 
			UserItemsAdsRepository userItemsAdsRepository, UserMedicalAdsRepository userMedicalAdsRepository,
			UserAdsToVoConverter userAdsToVoConverter, UserServiceAdsToVoConverter userServiceAdsToVoConverter) {
	
		
		this.userPetsAdsRepository = userPetsAdsRepository;
		this.userServiceAdsRepository = userServiceAdsRepository;
		this.userItemsAdsRepository = userItemsAdsRepository;
		this.userMedicalAdsRepository = userMedicalAdsRepository;
		this.userAdsToVoConverter = userAdsToVoConverter;
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

		if(type == AdsType.ACCESORIESS) {
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
	public List<UserAdsVO> getNearByAdsByCategory(AdsType adsType, Long Category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAdsVO findAdsById(Long id) {
		// TODO Auto-generated method stub
		return null;
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
	public BasicResponse createUserAds(UserAdsGeneralAdsRequest ads) {
		UserAds entity = this.userAdsToVoConverter.transformRequestToEntity(ads);
		if(ads.getType() == AdsType.ACCESORIESS) {
			this.userItemsAdsRepository.save((UserAccAds)entity);
		}
		else if(ads.getType() == AdsType.PET_CARE) {
			this.userMedicalAdsRepository.save((UserMedicalAds)entity);
		}
		else if(ads.getType() == AdsType.PETS) {
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

	
  
}
