package com.commerce.backend.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;

@Repository
public interface UserAdsRepository extends JpaRepository<UserAds, Long>, UserAdsRepositoryCustom {
   
   
   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND type = :type ")
	Page<UserAds> findByAdsType(@Param("type") AdsType type, Pageable pagable);
   
   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND type ='"+AdsType.Values.PETS+"'")
	Page<UserPetAds> findPetAdsType( Pageable pagable);

   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND type ='"+AdsType.Values.ACCESSORIES +"'")
	Page<UserAccAds> findAccAdsType(Pageable pagable);
   
   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND type ='"+AdsType.Values.PET_CARE+"'")
	Page<UserServiceAds> findPetCareAds(Pageable pagable);
   
}
