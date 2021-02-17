package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.dao.UserAdsRepository;
import com.commerce.backend.dao.UserItemsAdsRepository;
import com.commerce.backend.dao.UserMedicalAdsRepository;
import com.commerce.backend.dao.UserPetsAdsRepository;
import com.commerce.backend.dao.UserServiceAdsRepository;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.ServiceCategory;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserMedicalAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;
import com.commerce.backend.model.request.userAds.UserAdsGeneralAdsRequest;
import com.commerce.backend.model.request.userAds.UserAdsRequest;
import com.commerce.backend.model.request.userAds.UserServiceAdsRequest;
import com.commerce.backend.model.response.product.ProductDetailsResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class UserAdsServiceImpl implements UserAdsService {
	private UserAdsRepository userAdsRepository;
	private UserPetsAdsRepository userPetsAdsRepository;
	private UserServiceAdsRepository userServiceAdsRepository;
	private UserItemsAdsRepository userItemsAdsRepository;
	private UserMedicalAdsRepository userMedicalAdsRepository;
	
	@Autowired
	public UserAdsServiceImpl(UserAdsRepository userAdsRepository, UserPetsAdsRepository userPetsAdsRepository,
			UserServiceAdsRepository userServiceAdsRepository, 
			UserItemsAdsRepository userItemsAdsRepository, UserMedicalAdsRepository userMedicalAdsRepository) {
	
		this.userAdsRepository = userAdsRepository;
		this.userPetsAdsRepository = userPetsAdsRepository;
		this.userServiceAdsRepository = userServiceAdsRepository;
		this.userItemsAdsRepository = userItemsAdsRepository;
		this.userMedicalAdsRepository = userMedicalAdsRepository;
	}

	@Override
	public ProductDetailsResponse findByUrl(String url) {
		return null;
	}

	@Override
	public List<UserAdsVO> getAll(AdsType type, Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, String color) {
		Pageable pageable = PageRequest.of(page, size);
	
		if(type == AdsType.ACCESORIESS) {
			Page<UserAccAds> userAccAds = this.userItemsAdsRepository.findAll(pageable);
		}
		else if(type == AdsType.PET_CARE) {
		    Page<UserMedicalAds> userMedicalAds = this.userMedicalAdsRepository.findAll(pageable);
		}
		else if(type == AdsType.PETS) {
			Page<UserPetAds> userPetAds = this.userPetsAdsRepository.findAll(pageable);
		} 
		else if(type == AdsType.SERVICE) {
		    Page<UserServiceAds> userServiceAds = this.userServiceAdsRepository.findAll(pageable);	
		}
		if(type != AdsType.ALL || type != null) {
	     
		}
		 
		 
		 return null;
	}

	@Override
	public Long getAllCount(UserAdsVO userAdsVO, Float minPrice, Float maxPrice) {
		 Long count = this.userAdsRepository.countBy(userAdsVO, minPrice, maxPrice);
		return count;
	}

	@Override
	public List<UserAdsVO> getRelatedAds(UserAdsVO userAd) {
		List<UserAds> userAds = this.userAdsRepository.getReleatedAds(userAd);
		return null;
	}

	@Override
	public List<UserAdsVO> getNewlyAddedAds(AdsType adsType, Long Category) {
		this.userAdsRepository.getNewlyAddedAds(adsType, Category);
		return null;
	}

	@Override
	public List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, UserAdsVO adsCriteria) {
		List<UserAds> userAds = this.userAdsRepository.getNearByAds(page, size, sort, category, minPrice, maxPrice, adsCriteria);
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
	public List<UserAdsVO> getFavoriteAds(Long userId, String token) {
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
	public List<UserAdsVO> createUserAds(UserAdsGeneralAdsRequest ads) {
		UserAds entity;
		if(ads.getType() == AdsType.ACCESORIESS) {
			
		}
		else if(ads.getType() == AdsType.PET_CARE) {
			
		}
		else if(ads.getType() == AdsType.PETS) {
			
		}
		else if(ads.getType() == AdsType.SERVICE) {
			UserServiceAdsRequest request = ads.getUserServiceAdsRequest();
			entity = new UserServiceAds();
			entity.setActive(true);
			entity.setCreatedBy(null);
			entity.setDescription(request.getDescription());
			entity.setLatitude(request.getLatitude());
			entity.setName(request.getName());
			entity.setShort_description(request.getShort_description());
		    ServiceCategory category = new ServiceCategory();
		    
		    category.setId(request.getCategory_id());
		    
			((UserServiceAds)entity).setServiceCategory(category);
			category = new ServiceCategory();
			category.setId(request.getCategory_type_id());
			((UserServiceAds)entity).setServiceCategoryType(category);
			
		   this.userServiceAdsRepository.save((UserServiceAds)entity);
		}
		return null;
	}

	@Override
	public Long getAllCount(String category, Float minPrice, Float maxPrice, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	
  
}
