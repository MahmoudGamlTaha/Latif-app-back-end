package com.commerce.backend.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;

@Repository
public interface UserServiceAdsRepository extends CrudRepository<UserServiceAds, Long> {

}
