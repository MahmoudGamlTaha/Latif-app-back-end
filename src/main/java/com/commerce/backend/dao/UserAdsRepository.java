package com.commerce.backend.dao;

import org.springframework.data.domain.Pageable;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.UserAccAds;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserPetAds;
import com.commerce.backend.model.entity.UserServiceAds;

@NoRepositoryBean
public interface UserAdsRepository<T extends UserAds> extends JpaRepository<T, Long>, UserAdsRepositoryCustom<T> {
      
   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND uad.type ='"+AdsType.Values.PETS+"'")
	Page<UserPetAds> findPetAdsType( Pageable pagable);

   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND uad.type ='"+AdsType.Values.ACCESSORIES +"'")
	Page<UserAccAds> findAccAdsType(Pageable pagable);
   
   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND uad.type ='"+AdsType.Values.PET_CARE+"'")
	Page<UserServiceAds> findPetCareAds(Pageable pagable);
   @Query(value = "SELECT uad FROM UserAds uad WHERE uad.active = true AND uad.type = :type ")
    Page<T> findByAdsType(@Param("type") AdsType type, Pageable pagable);
  
  @Where(clause = "active = true")
 	Page<UserServiceAds> findAllByOrderByName(Pageable page);
   
}
