package com.commerce.backend.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.UserAds;

@Repository
public interface UserAdsRepository extends JpaRepository<UserAds, Long> {

}
