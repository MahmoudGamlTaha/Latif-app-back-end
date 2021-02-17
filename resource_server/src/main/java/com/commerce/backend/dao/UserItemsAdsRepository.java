package com.commerce.backend.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserPetAds;

@Repository
public interface UserItemsAdsRepository extends JpaRepository<UserPetAds, Long> {

}
