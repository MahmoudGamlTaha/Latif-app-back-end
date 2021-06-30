package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.category.ItemObjectCategoryResponseConverter;
import com.commerce.backend.model.dto.ItemObjectCategoryVO;
import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.request.category.CategoryUpdateRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;
import com.commerce.backend.service.cache.ItemObjectCategoryCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemObjectCategoryServiceImpl implements ItemObjectCategoryService {

    private final ItemObjectCategoryCacheService itemObjectCategoryCacheService;
    private final ItemObjectCategoryResponseConverter itemObjectCategoryResponseConverter;

    @Autowired
    public ItemObjectCategoryServiceImpl(ItemObjectCategoryCacheService itemObjectCategoryCacheService,
                                      ItemObjectCategoryResponseConverter itemObjectCategoryResponseConverter) {
        this.itemObjectCategoryCacheService = itemObjectCategoryCacheService;
        this.itemObjectCategoryResponseConverter = itemObjectCategoryResponseConverter;
    }

	@Override
	public BasicResponse findByCategoryId(Long id) {
		BasicResponse response = new BasicResponse();
		try {
		
		 ItemObjectCategory itemObject = itemObjectCategoryCacheService.findById(id);
		 HashMap<String,Object> responseValue = new HashMap<String, Object>(); 
		response.setSuccess(true);
		response.setMsg(MessageType.Success.getMessage());
		responseValue.put(MessageType.Data.getMessage(), itemObject);
		 response.setResponse(responseValue);
		}catch(Exception ex) {
			ex.printStackTrace();
			response.setMsg(ex.getMessage());
			response.setSuccess(false);
		}
		 return response;
	}
	@Override
	public ItemObjectCategory findById(Long id) {
		
		 ItemObjectCategory itemObject = itemObjectCategoryCacheService.findById(id);
	     return itemObject;
	}
		 
	@Override
    public BasicResponse findAllByOrderByName(Pageable pagable) {
        Page<ItemObjectCategory> productCategories = itemObjectCategoryCacheService.findAllByOrderByName(pagable);
       
        BasicResponse response = new BasicResponse();
        HashMap<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(MessageType.TotalItems.getMessage() , productCategories.getTotalElements());
        mapResponse.put(MessageType.CurrentPage.getMessage(), productCategories.getPageable().getPageNumber() + 1);
      
        List<ItemObjectCategoryResponse> itemObjectResponse = productCategories.get()
                .map(itemObjectCategoryResponseConverter)
                .collect(Collectors.toList());
        mapResponse.put(MessageType.Data.getMessage(), itemObjectResponse);
        response.setMsg(MessageType.Success.getMessage());
        response.setResponse(mapResponse);
        response.setSuccess(true);
        return response;
       
    }


	@Override
	public List<ItemObjectCategoryResponse> findPetsCategories() {
		List<PetCategory> petCategories = itemObjectCategoryCacheService.findPetsCategories();
		
		return petCategories
				.stream()
				.map(itemObjectCategoryResponseConverter)
				.collect(Collectors.toList());
	}


	@Override
	public List<ItemObjectCategoryResponse> findItemCategories() {
	List<ItemCategory> itemCategories = itemObjectCategoryCacheService.findItemCategories();
		
		return itemCategories
				.stream()
				.map(itemObjectCategoryResponseConverter)
				.collect(Collectors.toList());
	}


	@Override
	public BasicResponse createItemObjectCategory(CategoryRequest  request) {
		BasicResponse itemObjectResponse = itemObjectCategoryCacheService.createItemObjectCategory(request);
		return itemObjectResponse;
	}


	@Override
	public ItemObjectCategoryResponse deleteItemObjectCategory(Long id) {
		ItemObjectCategory itemObjectCategory = itemObjectCategoryCacheService.deleteItemObjectCategory(id);
		ItemObjectCategoryResponse itemObjectResponse = new ItemObjectCategoryResponse();
		ItemObjectCategoryVO itemObjectCategoryVO = new ItemObjectCategoryVO(itemObjectCategory);
		itemObjectResponse.setCategory(itemObjectCategoryVO);
		return itemObjectResponse;
	}


	@Override
	public BasicResponse findAllByTypeId(Integer id, Integer page) {
		return itemObjectCategoryCacheService.findAllByTypeId(id, page);
	}

	@Override
	public BasicResponse updateItemCategory(CategoryUpdateRequest request) {
		BasicResponse response = new BasicResponse();
		HashMap<String, Object> responseValue = new HashMap<String, Object>();
		ItemObjectCategory objectCategory = itemObjectCategoryCacheService.updateItemCategory(request);
		responseValue.put(MessageType.Data.getMessage(), objectCategory);
		response.setMsg(MessageType.Success.getMessage());
		response.setResponse(responseValue);
		response.setSuccess(true);
		return response;
	}

	@Override
	public List<ItemObjectCategoryResponse> findAllByParent(Long parent) {
	List<ItemObjectCategory> itemCategories = itemObjectCategoryCacheService.findCategoriesByParent(parent);
		
		return itemCategories
				.stream()
				.map(itemObjectCategoryResponseConverter)
				.collect(Collectors.toList());
	}
	
}
