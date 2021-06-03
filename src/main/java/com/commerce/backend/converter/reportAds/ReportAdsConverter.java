package com.commerce.backend.converter.reportAds;

import com.commerce.backend.constants.ReportType;
import com.commerce.backend.converter.UserAdsToVoConverter;
import com.commerce.backend.converter.user.UserResponseConverter;
import com.commerce.backend.model.dto.ReportAdsVo;
import com.commerce.backend.model.dto.ReportTypeVo;
import com.commerce.backend.model.entity.UserReportedAds;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReportAdsConverter implements Function<UserReportedAds, ReportTypeVo> {

    private final UserResponseConverter userResponseConverter;
    private final UserAdsToVoConverter userAdsToVoConverter;

    @Autowired
    public ReportAdsConverter(UserResponseConverter userResponseConverter, UserAdsToVoConverter userAdsToVoConverter) {
        this.userResponseConverter = userResponseConverter;
        this.userAdsToVoConverter = userAdsToVoConverter;
    }

    @Override
    public ReportTypeVo apply(UserReportedAds userReportedAds) {
        ReportTypeVo vo = new ReportTypeVo();
        if(userReportedAds.getReportType().equals(ReportType.REPORT)) {
            vo = new ReportAdsVo();
            ((ReportAdsVo)vo).setReason(userReportedAds.getReason());
        }
        vo.setUser(userResponseConverter.apply(userReportedAds.getUser()));
        vo.setAd(userReportedAds.getReportedAds());
        vo.setId(userReportedAds.getId());
        vo.setType(userReportedAds.getReportType());
        vo.setCreatedAt(userReportedAds.getCreatedAt());
        vo.setUpdatedAt(userReportedAds.getUpdatedAt());
        return vo;
    }
}
