package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.ReportType;
import com.commerce.backend.converter.reportAds.ReportAdsConverter;
import com.commerce.backend.dao.CustomUserAdsRepo;
import com.commerce.backend.dao.ReportReasonsRepository;
import com.commerce.backend.dao.ReportedAdsRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.dto.ReportTypeVo;
import com.commerce.backend.model.entity.ReportReasons;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserReportedAds;
import com.commerce.backend.model.request.report.ReportRequest;
import com.commerce.backend.model.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportAdsServiceImpl implements ReportAdsService{

    private final ReportedAdsRepository reportedAdsRepository;
    private final ReportAdsConverter reportAdsConverter;
    private final UserService userService;
    private final CustomUserAdsRepo userAdsRepo;
    private final ReportReasonsRepository reportReasonsRepository;

    @Autowired
    public ReportAdsServiceImpl(ReportedAdsRepository reportedAdsRepository, ReportAdsConverter reportAdsConverter, UserService userService, CustomUserAdsRepo userAdsRepo, ReportReasonsRepository reportReasonsRepository) {
        this.reportedAdsRepository = reportedAdsRepository;
        this.reportAdsConverter = reportAdsConverter;
        this.userService = userService;
        this.userAdsRepo = userAdsRepo;
        this.reportReasonsRepository = reportReasonsRepository;
    }

    @Override
    public BasicResponse getAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<UserReportedAds> reportedAds = reportedAdsRepository.findAll(pageable);
        List<ReportTypeVo> collect = new ArrayList<>();
        reportedAds.forEach((ad)-> collect.add(reportAdsConverter.apply(ad)));
        return resHelper.res(collect, true, MessageType.Success.getMessage(), pageable);
    }

    @Override
    public BasicResponse create(ReportRequest request) {
        String principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByMobileNumber(principle);
        if(user == null || request.getAdId() == null || request.getType() == null){
            return resHelper.res(null , false, MessageType.Missing.getMessage(), null);
        }
        UserAds userAds = userAdsRepo.findById(request.getAdId()).orElse(null);
        if(userAds != null) {
            List<UserReportedAds> reportAds = reportedAdsRepository.findByUserAndAds(user, userAds);
            Long reportCount = reportAds.stream()
                    .filter(ad -> ad.getReportType() == request.getType())
                    .count();
            if(reportCount > 0) {
                return resHelper.res(null, false, MessageType.Fail.getMessage(), null);
            }
            UserReportedAds reportedAds = new UserReportedAds();
            reportedAds.setUser(user);
            reportedAds.setAds(userAds);
            if(request.getType().equals(ReportType.REPORT)){
                ReportReasons reportReasons = reportReasonsRepository.findById(request.getReason()).orElse(null);
                if(reportReasons != null) {
                    reportedAds.setReason(reportReasons);
                }
            }
            reportedAds.setReportType(request.getType());
            reportedAds.setCreatedAt(new Date());
            ReportTypeVo vo = reportAdsConverter.apply(reportedAdsRepository.save(reportedAds));
            return resHelper.res(vo, true, MessageType.Success.getMessage(), null);
        }
        return resHelper.res(null , false, MessageType.Fail.getMessage(), null);
    }

    @Override
    public BasicResponse getReportedAds(ReportType type,Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        String principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByMobileNumber(principle);
        Page<UserReportedAds> reportedAds = reportedAdsRepository.findByUserAndReportType(user, type, pageable);
        List<ReportTypeVo> collect = new ArrayList<>();
        reportedAds.forEach((ad)-> collect.add(reportAdsConverter.apply(ad)));
        return resHelper.res(collect, true, MessageType.Success.getMessage(), pageable);
    }

    @Override
    public BasicResponse removeFromInterestedList(Long id) {
        try{
            if(id == null){
                return resHelper.res(null , false, MessageType.Missing.getMessage(), null);
            }
            String principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = userService.findUserByMobileNumber(principle);
            UserReportedAds reportedAds = reportedAdsRepository.findById(id).orElse(null);
            if(reportedAds != null && reportedAds.getReportType().equals(ReportType.INTEREST) && user == reportedAds.getUser()) {
                reportedAdsRepository.deleteById(id);
                return resHelper.res("Deleted Successfully!", true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null , false, MessageType.Fail.getMessage(), null);
            }
        }catch(Exception ex){
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }
}
