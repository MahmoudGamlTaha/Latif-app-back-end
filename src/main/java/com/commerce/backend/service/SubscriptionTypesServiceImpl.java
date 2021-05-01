package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.subscription.SubscriptionConverter;
import com.commerce.backend.dao.SubscriptionTypesRepository;
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
            return res(collect, true, "Success", pageable);
        }catch (Exception ex)
        {
            return res(ex, false, "Error", null);
        }
    }

    @Override
    public BasicResponse findById(Long id) {
        try {
            if (id == null) {
                return res(null, false, "invalid id", null);
            }
            SubscriptionTypes sub = repository.findById(id).orElse(null);
            if(sub != null){
                SubscriptionTypeVO vo = converter.apply(sub);
                return res(vo, true, "Success", null);
            }else{
                return res(null, true, "Not Found", null);
            }
        }catch(Exception ex){
            return res(ex, false, "Error", null);
        }
    }

    @Override
    public BasicResponse create(SubscriptionTypeRequest request) {
        SubscriptionTypes entity = converter.requestToEntity(request);
        SubscriptionTypes sub = repository.save(entity);
        SubscriptionTypeVO vo = converter.apply(sub);
        return res(vo, true, "Success", null);
    }

    @Override
    public BasicResponse update(SubscriptionTypeRequest request, Long id) {
        if (id == null) {
            return res(null, false, "Invalid Id", null);
        }
        SubscriptionTypes sub = repository.findById(id).orElse(null);
        if(sub != null){
            SubscriptionTypes entity = converter.update(request, sub);
            SubscriptionTypeVO vo = converter.apply(repository.save(entity));
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
            SubscriptionTypes sub = repository.findById(id).orElse(null);
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
