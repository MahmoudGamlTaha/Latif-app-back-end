package com.commerce.backend.dao;
import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMedicalAdsRepository extends JpaRepository<UserMedicalAds, Long> {
}
