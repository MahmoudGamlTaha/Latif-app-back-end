package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.request.userAds.DynamicAdsRequest;
import com.commerce.backend.model.request.userAds.UserPetsAdsRequest;
import com.commerce.backend.model.request.userAds.adTypeRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    
    List<UserAdsVO> getFavoriteAds(Long userId ,String token);
    
    List<UserAdsVO> getInterested(Long userId, String token);
  
    BasicResponse createUserAds(DynamicAdsRequest<String, String> ads, List<MultipartFile> file);
    

    List<UserAdsVO> searchItemDisplay(String keyword, Integer page, Integer size);

	Long getAllCount(String category, Float minPrice, Float maxPrice, String color);

    JSONObject getPetsResponse(adTypeRequest petType) throws Exception;

    <T> UserAdsVO savePet(UserPetsAdsRequest userPetsAdsRequest);
}
