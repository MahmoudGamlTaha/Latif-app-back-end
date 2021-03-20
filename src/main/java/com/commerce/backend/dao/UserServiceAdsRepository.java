package com.commerce.backend.dao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backend.model.entity.UserServiceAds;

@Repository
@Transactional
public interface UserServiceAdsRepository extends UserAdsRepository<UserServiceAds>, UserServiceAdsRepoCustom {
   
}
