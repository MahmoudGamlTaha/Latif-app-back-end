package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.response.product.ProductDetailsResponse;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class UserAdsServiceImpl implements UserAdsService {

	@Override
	public ProductDetailsResponse findByUrl(String url) {
		return null;
	}

	@Override
	public List<UserAdsVO> getAll(AdsType type,Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getAllCount(String category, Float minPrice, Float maxPrice, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAdsVO> getRelatedAds(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAdsVO> getNewlyAddedAds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAdsVO> getNearByAdsByCategory(Long category) {
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
	public List<UserAdsVO> createUserAds(UserAdsVO ads) {
		// TODO Auto-generated method stub
		return null;
	}

	
  
}
