package com.commerce.backend.service;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.MessageType;
import com.commerce.backend.dao.*;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final CustomUserAdsRepo UserAdsRepo;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final CustomUserAdsCriteriaHelper customUserAdsCriteriaHelper;
    @Autowired
    public DashboardServiceImpl(CustomUserAdsRepo userAdsRepo, UserSubscriptionRepository userSubscriptionRepository,
                                CustomUserAdsCriteriaHelper customUserAdsCriteriaHelper) {
        UserAdsRepo = userAdsRepo;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.customUserAdsCriteriaHelper = customUserAdsCriteriaHelper;
    }

    @Override
    public BasicResponse getDashboard() {
        Long getAllAdsCount = UserAdsRepo.countByActiveTrue();
        Long getPetsAdsCount = UserAdsRepo.countByActiveTrueAndType(AdsType.PETS);
        Long getPetCareAdsCount = UserAdsRepo.countByActiveTrueAndType(AdsType.PET_CARE);
        Long getAccAdsCount = UserAdsRepo.countByActiveTrueAndType(AdsType.ACCESSORIES);
        Long getServiceAdsCount = UserAdsRepo.countByActiveTrueAndType(AdsType.SERVICE);
        Date now = new Date();
        Long userSubscriptionsCount = userSubscriptionRepository.countByEndDateGreaterThan(now);
        HashMap<String, Object> map = new HashMap<>();
        map.put("AllAdsCount", getAllAdsCount);
        map.put("PetAdsCount", getPetsAdsCount);
        map.put("PetCareAdsCount", getPetCareAdsCount);
        map.put("AccessoriesAdsCount", getAccAdsCount);
        map.put("ServiceAdsCount", getServiceAdsCount);
        map.put("userSubscriptionsCount", userSubscriptionsCount);
        return resHelper.res(map, true, MessageType.Success.getMessage(), null);
    }

    @Override
    public BasicResponse getAdsCountByCategoryId(Long id) {
        HashMap<String, Object> map = new HashMap<>();
        if(id == null){
            return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
        }
        Object AdsCount = customUserAdsCriteriaHelper.getCountByCategory(id);
        map.put("AdsCount", AdsCount);
        return resHelper.res(map, true, MessageType.Success.getMessage(), null);
    }

}
