package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.request.userAds.UserAdsGeneralAdsRequest;
import com.commerce.backend.model.request.userAds.UserAdsRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import com.commerce.backend.model.response.product.ProductResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UserAdsService {
    ProductDetailsResponse findByUrl(String url);

    BasicResponse getAll(AdsType type ,Integer page, Integer size, String sort, Long category, Float minPrice, Float maxPrice);
   
    Long getAllCount(UserAdsVO userAdsVO, Float minPrice, Float maxPrice);
    
    UserAdsVO findAdsById(Long id);

    List<UserAdsVO> getRelatedAds(UserAdsVO userAds);

    List<UserAdsVO> getNewlyAddedAds(AdsType adsType, Long Category);
    
    List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, UserAdsVO adsCriteria);

    List<UserAdsVO> getNearByAdsByCategory(AdsType adsType, Long Category);
    
  
    
    List<UserAdsVO> getInterested(Long userId, String token);
  
    BasicResponse createUserAds(UserAdsGeneralAdsRequest ads);
    

    List<UserAdsVO> searchItemDisplay(String keyword, Integer page, Integer size);

	Long getAllCount(String category, Float minPrice, Float maxPrice, String color);
   
}
