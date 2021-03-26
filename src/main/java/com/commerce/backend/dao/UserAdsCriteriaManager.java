package com.commerce.backend.dao;

import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.UserAds;

@Component
public class UserAdsCriteriaManager implements Consumer<UserAdsVO> {
	@PersistenceContext
	EntityManager entityManager;
	CriteriaBuilder builder;
	CriteriaQuery<UserAds> userAdsQuery;
	Root rootCriteria;
	
	@PostConstruct
	public void init(){
		builder = entityManager.getCriteriaBuilder();
		userAdsQuery = builder.createQuery(UserAds.class);
		rootCriteria = userAdsQuery.from(UserAds.class);
	}

	@Override
	public void accept(UserAdsVO arg0) {
		
		
	}
	
	 
	
}
