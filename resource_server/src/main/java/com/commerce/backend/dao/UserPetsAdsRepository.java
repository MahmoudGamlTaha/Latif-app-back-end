package com.commerce.backend.dao;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.UserPetAds;

@Repository
public interface UserPetsAdsRepository extends JpaRepository<UserPetAds, Long> {
    @Override
	List<UserPetAds> findAll();
    
    @Override
    Page<UserPetAds> findAll(Pageable pageable );
}
