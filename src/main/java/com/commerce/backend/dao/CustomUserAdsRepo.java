package com.commerce.backend.dao;

import com.commerce.backend.model.entity.UserAds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomUserAdsRepo extends JpaRepository<UserAds, Long> {
    @Query(value="SELECT user_ads.*, ST_Distance(geom, poi)/1000 AS distance_km " +
            "FROM user_ads," +
            "(select ST_MakePoint(?1,?2) as poi) as poi\n" +
            "WHERE ST_DWithin(geom, poi, 100000)\n" +
            "ORDER BY ST_Distance(geom, poi)\n" +
            "LIMIT 10;", nativeQuery = true)
    List<UserAds> findNearest(double longitude, double latitude, Pageable pageable);
}
