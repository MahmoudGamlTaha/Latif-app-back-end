package com.commerce.backend.converter.subscription;

import com.commerce.backend.converter.user.UserResponseConverter;
import com.commerce.backend.dao.SubscriptionTypesRepository;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.model.dto.SubscriptionTypeVO;
import com.commerce.backend.model.dto.UserSubscriptionVO;
import com.commerce.backend.model.entity.SubscriptionTypes;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserSubscription;
import com.commerce.backend.model.request.subscription.UserSubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;


@Component
public class UserSubscriptionConverter implements Function<UserSubscription, UserSubscriptionVO> {

    private final SubscriptionTypesRepository subRepository;
    private final UserRepository userRepository;
    private final UserResponseConverter userConverter;
    private final SubscriptionConverter converter;

    @Autowired
    public UserSubscriptionConverter(SubscriptionTypesRepository subRepository, UserRepository userRepository, UserResponseConverter userConverter, SubscriptionConverter converter) {
        this.subRepository = subRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.converter = converter;
    }

    @Override
    public UserSubscriptionVO apply(UserSubscription userSubscription) {
        UserSubscriptionVO vo = new UserSubscriptionVO();
        vo.setId(userSubscription.getId());
        vo.setUser(userConverter.apply(userSubscription.getUserId()));
        SubscriptionTypeVO subVo = converter.apply(userSubscription.getSubscriptionId());
        vo.setSubscription(subVo);
        vo.setStartDate(userSubscription.getStartDate());
        vo.setEndDate(userSubscription.getEndDate());
        vo.setCreatedAt(userSubscription.getCreatedAt());
        vo.setUpdatedAt(userSubscription.getUpdatedAt());
        return vo;
    }

    public UserSubscription requestToEntity(UserSubscriptionRequest request)
    {
        UserSubscription userSubscription = new UserSubscription();
        User user = userRepository.findById(request.getUserId()).orElse(null);
        SubscriptionTypes subType = subRepository.findById(request.getSubscriptionId()).orElse(null);
        if (subType != null && user != null) {
            userSubscription.setUserId(user);
            userSubscription.setSubscriptionId(subType);
            userSubscription.setStartDate(new Date());
            Date currentDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, subType.getPeriodInDays());
            Date currentDatePlusOne = c.getTime();
            userSubscription.setEndDate(currentDatePlusOne);
            userSubscription.setCreatedAt(new Date());
        }
        return userSubscription;
    }

    public UserSubscription update(UserSubscriptionRequest request, UserSubscription entity){
        if(request.getUserId() != null) {
            userRepository.findById(request.getUserId()).ifPresent(entity::setUserId);
        }
        if(request.getSubscriptionId() != null) {
            SubscriptionTypes subType = subRepository.findById(request.getSubscriptionId()).orElse(null);
            entity.setSubscriptionId(subType);
        }
        entity.setUpdatedAt(new Date());
        return entity;
    }
}
