package com.commerce.backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.commerce.backend.model.entity.UserServiceAds;

public class UserServiceAdsRepoCustomImpl implements UserServiceAdsRepoCustom {

	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public List<UserServiceAds> findAllMobile() {
		List<UserServiceAds> userServiceAds = entityManager.createQuery("SELECT sads FROM UserServiceAds sads WHERE sads.active = true").getResultList();
		return userServiceAds;
	}

}
