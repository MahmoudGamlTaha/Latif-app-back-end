package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.request.userAds.*;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserAdsService {
    ProductDetailsResponse findByUrl(String url);
    @Deprecated
    BasicResponse getAll(AdsType type , LocationRequest location, Integer page, Integer size, String sort, Long category, Float minPrice, Float maxPrice);
   
    Long getAllCount(UserAdsVO userAdsVO, Float minPrice, Float maxPrice);

    BasicResponse findAdsById(Long id) throws Exception;

    List<UserAdsVO> getRelatedAds(UserAdsVO userAds);

    List<UserAdsVO> getNewlyAddedAds(AdsType adsType, Long Category);
    
    List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, UserAdsVO adsCriteria);

    List<UserAdsVO> getNearByAdsByCategory(AdsType adsType, Long Category);
    
    List<UserAdsVO> getInterested(Long userId, String token);
  
    BasicResponse createUserAds(DynamicAdsRequest<String, Object> ads,List<String> xfiles ,List<MultipartFile> file, boolean external);
    
    List<UserAdsVO> searchItemDisplay(String keyword, Integer page, Integer size);

	Long getAllCount(String category, Float minPrice, Float maxPrice, String color);

    BasicResponse getCreateForm(adTypeRequest petType, Long category);
    
    BasicResponse getFilterForm(adTypeRequest petType, Long category);

    <T> UserAdsVO savePet(UserPetsAdsRequest userPetsAdsRequest);

    BasicResponse adsFiltration(AdsFiltrationRequest<String, Object> request, Pageable pageable);

    BasicResponse findNearby(AdsType Type, Double longitude, Double latitude, Integer distance, Integer page, Integer size, Long cat_id);

    BasicResponse updateUserAds(UpdateAdRequest<String, Object> request, List<String> fileList, List<MultipartFile> files);

    BasicResponse myAds(Integer page, Integer size);

    BasicResponse adActivation(Long id, boolean activate);
}
