package com.commerce.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.UserAds;

@Repository
public interface UserAdsRepositoryCustom{
	 Long countBy(UserAdsVO userAdsVO, Float minPrice, Float maxPrice);
     
     List<UserAds> getReleatedAds(UserAdsVO userAds);
     List<UserAds> getNewlyAddedAds(AdsType adsType, Long Category);
     List<UserAds> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
 			Float maxPrice, UserAdsVO adsCriteria);
     List<UserAds> getFavoriteAds(Long userId ,String token);
     
     List<UserAds> getInterested(Long userId, String token);
   
     List<UserAds> createUserAds(UserAdsVO ads);

     List<UserAds> searchItemDisplay(String keyword, Integer page, Integer size);
}
