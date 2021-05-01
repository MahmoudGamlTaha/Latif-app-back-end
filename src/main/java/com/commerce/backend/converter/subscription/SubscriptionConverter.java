package com.commerce.backend.converter.subscription;

import com.commerce.backend.model.dto.SubscriptionTypeVO;
import com.commerce.backend.model.entity.SubscriptionTypes;
import com.commerce.backend.model.request.subscription.SubscriptionTypeRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SubscriptionConverter implements Function<SubscriptionTypes, SubscriptionTypeVO> {
    @Override
    public SubscriptionTypeVO apply(SubscriptionTypes subscriptionTypes) {
        SubscriptionTypeVO vo = new SubscriptionTypeVO();
        vo.setId(subscriptionTypes.getId());
        vo.setName(subscriptionTypes.getName());
        vo.setDescription(subscriptionTypes.getDescription());
        vo.setPrice(subscriptionTypes.getPrice());
        vo.setAdsNumber(subscriptionTypes.getAdsNumber());
        vo.setPeriodInDays(subscriptionTypes.getPeriodInDays());
        return vo;
    }

    public SubscriptionTypes requestToEntity(SubscriptionTypeRequest request)
    {
        SubscriptionTypes subscriptionTypes = new SubscriptionTypes();
        subscriptionTypes.setName(String.valueOf(request.getName()));
        subscriptionTypes.setDescription(String.valueOf(request.getDescription()));
        subscriptionTypes.setPrice(Float.parseFloat(String.valueOf(request.getPrice())));
        subscriptionTypes.setAdsNumber(request.getAdsNumber());
        subscriptionTypes.setPeriodInDays(request.getPeriodInDays());
        return subscriptionTypes;
    }

    public SubscriptionTypes update(SubscriptionTypeRequest request, SubscriptionTypes entity) {

        if(request.getName() != null) {
            entity.setName(String.valueOf(request.getName()));
        }
        if(request.getDescription() != null) {
            entity.setDescription(String.valueOf(request.getDescription()));
        }
        if(request.getPrice() != null) {
            entity.setPrice(Float.parseFloat(String.valueOf(request.getPrice())));
        }
        if(request.getAdsNumber() != null) {
            entity.setAdsNumber(request.getAdsNumber());
        }
        if(request.getPeriodInDays() != null) {
            entity.setPeriodInDays(request.getPeriodInDays());
        }
        return entity;
    }
}
