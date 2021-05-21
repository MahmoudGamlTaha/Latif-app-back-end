package com.commerce.backend.dao;

import com.commerce.backend.constants.ReportType;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserReportedAds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedAdsRepository extends JpaRepository<UserReportedAds, Long> {
    Page<UserReportedAds> findAll(Pageable pageable);
    List<UserReportedAds> findByUserAndAds(User user, UserAds userAds);
    Page<UserReportedAds> findByUserAndReportType(User user, ReportType reportType, Pageable pageable);
}
