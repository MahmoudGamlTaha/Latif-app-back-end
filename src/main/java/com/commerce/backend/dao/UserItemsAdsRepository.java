package com.commerce.backend.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.commerce.backend.model.entity.UserAccAds;

@Repository
public interface UserItemsAdsRepository extends JpaRepository<UserAccAds, Long> {

}
