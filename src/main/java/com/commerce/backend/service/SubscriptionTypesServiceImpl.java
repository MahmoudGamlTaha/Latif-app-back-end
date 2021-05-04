package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.subscription.SubscriptionConverter;
import com.commerce.backend.dao.SubscriptionTypesRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.dto.SubscriptionTypeVO;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.entity.SubscriptionTypes;
import com.commerce.backend.model.request.subscription.SubscriptionTypeRequest;
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
public class SubscriptionTypesServiceImpl implements SubscriptionTypesService{

    private final SubscriptionTypesRepository repository;
    private final SubscriptionConverter converter;

    @Autowired
    public SubscriptionTypesServiceImpl(SubscriptionTypesRepository repository, SubscriptionConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public BasicResponse findAll(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<SubscriptionTypeVO> collect = new ArrayList<>();
            Page<SubscriptionTypes> subTypes = repository.findAll(pageable);
            subTypes.forEach(type -> collect.add(converter.apply(type)));
            return resHelper.res(collect, true, MessageType.Success.getMessage(), pageable);
        }catch (Exception ex)
        {
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse findById(Long id) {
        try {
            if (id == null) {
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }
            SubscriptionTypes sub = repository.findById(id).orElse(null);
            if(sub != null){
                SubscriptionTypeVO vo = converter.apply(sub);
                return resHelper.res(vo, true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null, true, MessageType.Missing.getMessage(), null);
            }
        }catch(Exception ex){
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse create(SubscriptionTypeRequest request) {
        SubscriptionTypes entity = converter.requestToEntity(request);
        SubscriptionTypes sub = repository.save(entity);
        SubscriptionTypeVO vo = converter.apply(sub);
        return resHelper.res(vo, true, MessageType.Success.getMessage(), null);
    }

    @Override
    public BasicResponse update(SubscriptionTypeRequest request, Long id) {
        if (id == null) {
            return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
        }
        SubscriptionTypes sub = repository.findById(id).orElse(null);
        if(sub != null){
            SubscriptionTypes entity = converter.update(request, sub);
            SubscriptionTypeVO vo = converter.apply(repository.save(entity));
            return resHelper.res(vo, true, MessageType.Success.getMessage(), null);
        }else{
            return resHelper.res(null, true, MessageType.Fail.getMessage(), null);
        }
    }

    @Override
    public BasicResponse delete(Long id) {
        try {
            if (id == null) {
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }
            SubscriptionTypes sub = repository.findById(id).orElse(null);
            if(sub != null){
                repository.delete(sub);
                return resHelper.res("Deleted Successfully!", true, MessageType.Success.getMessage(), null);
            }else{
                return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
            }
        }catch(Exception ex){
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }

}
