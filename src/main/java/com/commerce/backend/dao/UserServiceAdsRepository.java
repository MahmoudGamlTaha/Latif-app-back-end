package com.commerce.backend.dao;
import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.ServiceCategory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backend.model.entity.UserServiceAds;

@Repository
@Transactional
public interface UserServiceAdsRepository extends UserAdsRepository<UserServiceAds>, UserServiceAdsRepoCustom {
}
