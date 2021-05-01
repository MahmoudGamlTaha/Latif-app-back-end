package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.subscription.UserSubscriptionConverter;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.dao.UserSubscriptionRepository;
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

import java.util.ArrayList;
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
            return res(collect, true, "Success", pageable);
        }catch (Exception ex)
        {
            return res(ex, false, "Error", null);
        }
    }

    @Override
    public BasicResponse findById(Long id) {
        try {
            if (id != null) {
                UserSubscription sub = repository.findById(id).orElse(null);
                if(sub != null) {
                    UserSubscriptionVO vo = converter.apply(sub);
                    return res(vo, true, "Success", null);
                }else{
                    return res(null, true, "Not Found", null);
                }
            }else{
                return res(null, true, "Not Found", null);
            }
        }catch (Exception ex)
        {
            return res(ex, false, "Error", null);
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
            return res(collect, true, "Success", pageable);
        }catch (Exception ex)
        {
            return res(ex, false, "Error", null);
        }
    }

    @Override
    public BasicResponse create(UserSubscriptionRequest request) {
        try {
            UserSubscription entity = converter.requestToEntity(request);
            UserSubscription repo = repository.save(entity);
            return res(converter.apply(repo), true, "Success", null);
        }catch(Exception ex){
            return res(ex, false, "Failed", null);
        }
    }

    @Override
    public BasicResponse update(UserSubscriptionRequest request, Long id) {
        if (id == null) {
            return res(null, false, "Invalid Id", null);
        }
        UserSubscription entity = repository.findById(id).orElse(null);
        if(entity != null){
            UserSubscription sub = converter.update(request, entity);
            UserSubscriptionVO vo = converter.apply(repository.save(sub));
            return res(vo, true, "Success", null);
        }else{
            return res(null, true, "Not Found", null);
        }
    }

    @Override
    public BasicResponse delete(Long id) {
        try {
            if (id == null) {
                return res(null, false, "Invalid Id", null);
            }
            UserSubscription sub = repository.findById(id).orElse(null);
            if(sub != null){
                repository.delete(sub);
                return res("Deleted Successfully!", true, "Success", null);
            }else{
                return res(null, true, "Not Found", null);
            }
        }catch(Exception ex){
            return res(ex, false, "Error", null);
        }
    }

    public BasicResponse res(Object obj, boolean success, String msg, Pageable pageable)
    {
        BasicResponse res = new BasicResponse();
        HashMap<String, Object> map = new HashMap<>();

        if( obj instanceof Exception) {
            map.put(MessageType.Data.getMessage(), ((Exception) obj).getStackTrace());
        } else {
            map.put(MessageType.Data.getMessage(),  obj);
            if(pageable != null) {
                map.put(MessageType.CurrentPage.getMessage(), pageable.getPageNumber());
            }
        }
        res.setMsg(msg);
        res.setSuccess(success);
        res.setResponse(map);
        return res;
    }
}
