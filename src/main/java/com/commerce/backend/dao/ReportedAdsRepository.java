package com.commerce.backend.dao;

import com.commerce.backend.constants.ReportType;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserReportedAds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedAdsRepository extends JpaRepository<UserReportedAds, Long> {
	 @Query(value="SELECT ur.* FROM user_reported_ads ur WHERE ur.type = 1", countQuery = "SELECT count(*) from user_reported_ads ur WHERE ur.type = 1",
			 nativeQuery = true)
     Page<UserReportedAds> findAllReport(Pageable pageable);
    
	 @Query(value="SELECT ur.* FROM user_reported_ads ur WHERE ur.user_id = ?1 AND ur.ads_id = ?2", nativeQuery = true)
     UserReportedAds findByUserAndAds(Long user, Long userAds);
    
	 Page<UserReportedAds> findByUserAndReportType(User user, ReportType reportType, Pageable pageable);
    UserReportedAds save(UserReportedAds ura);
}
