package com.commerce.backend.dao;

import com.commerce.backend.model.entity.UserAds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserAdsRepo extends JpaRepository<UserAds, Long> {
}
