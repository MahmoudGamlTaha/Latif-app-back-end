package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.subscription.UserSubscriptionConverter;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.dao.UserSubscriptionRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.dto.SubscriptionTypeVO;
import com.commerce.backend.model.dto.UserSubscriptionVO;
import com.commerce.backend.model.entity.SubscriptionTypes;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserSubscription;
import com.commerce.backend.model.request.subscription.UserSubscriptionRequest;
import com.commerce.backend.model.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService{
    private final UserSubscriptionRepository repository;
    private final UserRepository userRepository;
    private final UserSubscriptionConverter converter;
    @Autowired
    public UserSubscriptionServiceImpl(UserSubscriptionRepository repository, UserRepository userRepository, UserSubscriptionConverter converter) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public BasicResponse findByUserId(Long userId, Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            User user = userRepository.findById(userId).orElse(null);
            List<UserSubscriptionVO> collect = new ArrayList<>();
            if (user != null) {
                Page<UserSubscription> sub = repository.findUserSubscriptionByUserId(user, pageable);
                sub.forEach(sb -> collect.add(converter.apply(sb)));
            }
            return resHelper.res(collect, true, MessageType.Success.getMessage(), pageable);
        }catch (Exception ex)
        {
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse findById(Long id) {
        try {
            if (id != null) {
                UserSubscription sub = repository.findById(id).orElse(null);
                if(sub != null) {
                    UserSubscriptionVO vo = converter.apply(sub);
                    return resHelper.res(vo, true, MessageType.Success.getMessage(), null);
                }else{
                    return resHelper.res(null, true, MessageType.Missing.getMessage(), null);
                }
            }else{
                return resHelper.res(null, true, MessageType.Missing.getMessage(), null);
            }
        }catch (Exception ex)
        {
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse findBySubscriptionId(Long SubscriptionTypeId, Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            User user = userRepository.findById(SubscriptionTypeId).orElse(null);
            List<UserSubscriptionVO> collect = new ArrayList<>();
            if (user != null) {
                Page<UserSubscription> sub = repository.findUserSubscriptionByUserId(user, pageable);
                sub.forEach(sb -> collect.add(converter.apply(sb)));
            }
            return resHelper.res(collect, true, MessageType.Success.getMessage(), pageable);
        }catch (Exception ex)
        {
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse create(UserSubscriptionRequest request) {
        try {
            User user = userRepository.findById(request.getUserId()).orElse(null);
            Date currentDate = new Date();
            SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/dd-M-yyyy hh:mm:ss");
            UserSubscription userSubscriptions = repository.findByUserId(user, currentDate);
           if(userSubscriptions != null)
           {
               return resHelper.res(null, false, MessageType.Fail.getMessage(), null);
           }else {
               UserSubscription entity = converter.requestToEntity(request);
               UserSubscription repo = repository.save(entity);
               return resHelper.res(converter.apply(repo), true, MessageType.Success.getMessage(), null);
           }
        }catch(Exception ex){
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse update(UserSubscriptionRequest request, Long id) {
        if (id == null) {
            return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
        }
        UserSubscription entity = repository.findById(id).orElse(null);
        if(entity != null){
            UserSubscription sub = converter.update(request, entity);
            UserSubscriptionVO vo = converter.apply(repository.save(sub));
            return resHelper.res(vo, true, MessageType.Success.getMessage(), null);
        }else{
            return resHelper.res(null, true, MessageType.Missing.getMessage(), null);
        }
    }

    @Override
    public BasicResponse delete(Long id) {
        try {
            if (id == null) {
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }
            UserSubscription sub = repository.findById(id).orElse(null);
            if(sub != null){
                repository.delete(sub);
                return resHelper.res("Deleted Successfully!", true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null, true, MessageType.Missing.getMessage(), null);
            }
        }catch(Exception ex){
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }
}
