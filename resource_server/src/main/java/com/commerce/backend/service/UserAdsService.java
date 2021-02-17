package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import com.commerce.backend.model.response.product.ProductResponse;

import java.util.List;

public interface UserAdsService {
    ProductDetailsResponse findByUrl(String url);

    List<UserAdsVO> getAll(AdsType type ,Integer page, Integer size, String sort, Long category, Float minPrice, Float maxPrice, String color);
   
    Long getAllCount(String category, Float minPrice, Float maxPrice, String color);
    
    UserAdsVO findAdsById(Long id);

    List<UserAdsVO> getRelatedAds(String url);

    List<UserAdsVO> getNewlyAddedAds();
    
    List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice, Float maxPrice, String color);

    List<UserAdsVO> getNearByAdsByCategory(Long category);
    
    List<UserAdsVO> getFavoriteAds(Long userId ,String token);
    
    List<UserAdsVO> getInterested(Long userId, String token);
  
    List<UserAdsVO> createUserAds(UserAdsVO ads);

    List<UserAdsVO> searchItemDisplay(String keyword, Integer page, Integer size);
   
}
