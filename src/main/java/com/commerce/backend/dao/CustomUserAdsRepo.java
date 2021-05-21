package com.commerce.backend.dao;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserPetAds;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
// criteria bulider 
// check this link https://stackoverflow.com/questions/29604734/how-to-use-native-sql-as-a-fragment-where-clause-of-a-bigger-query-made-with-c
@Repository
public interface CustomUserAdsRepo extends JpaRepository<UserAds, Long> {

    @Query(value="SELECT user_ads.*, ST_Distance(geom, poi)/1000 AS distance_km " +
            "FROM user_ads, " +
            "(select ST_MakePoint(?1,?2) as poi) as poi " +
            "WHERE ST_DWithin(geom, poi, 100000) AND type=?3 " +
            "ORDER BY ST_Distance(geom, poi)",
            countQuery = "SELECT count(*) from user_ads, (select ST_MakePoint(?1,?2) as poi) as poi  WHERE ST_DWithin(geom, poi, 100000) AND type=?3",
            nativeQuery = true)
    Page<UserAds> findAll(double longitude, double latitude, String type, Pageable pageable );

    @Query(value="SELECT user_ads.*, ST_Distance(geom, poi)/1000 AS distance_km " +
            "FROM user_ads," +
            "(select ST_MakePoint(?1,?2) as poi) as poi\n" +
            "WHERE ST_DWithin(geom, poi, ?3)\n" +
            "ORDER BY ST_Distance(geom, poi)",
            countQuery = "SELECT count(*) from user_ads, (select ST_MakePoint(?1,?2) as poi) as poi  WHERE ST_DWithin(geom, poi, ?3)",
            nativeQuery = true)
    List<UserAds> findNearest(double longitude, double latitude, Integer distance, Pageable pageable);


    Page<UserAds> findUserAdsByType(String type, Pageable pageable);
    @Query("SELECT ud from UserAds ud where id = ?1")
    Optional<UserAds> findById(Long id);
    
    Long countByActiveTrue();
    Long countByActiveTrueAndType(AdsType adsType);

    Page<UserAds> findByCreatedByAndActiveTrue(User user, Pageable pageable);
}
