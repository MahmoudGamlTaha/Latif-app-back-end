package com.commerce.backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.UserAds;

@Repository
public class UserAdsRepositoryCustomImpl implements UserAdsRepositoryCustom {

	
	@Override
	public Long countBy(UserAdsVO userAdsVO, Float minPrice, Float maxPrice) {
	
		return null;
	}

	@Override
	public List<UserAds> getReleatedAds(UserAdsVO userAds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAds> getNewlyAddedAds(AdsType adsType, Long Category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAds> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, UserAdsVO adsCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAds> getFavoriteAds(Long userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAds> getInterested(Long userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAds> createUserAds(UserAdsVO ads) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAds> searchItemDisplay(String keyword, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

}
