package com.commerce.backend.service;

import com.commerce.backend.converter.category.ItemObjectCategoryResponseConverter;
import com.commerce.backend.dao.ItemObjectCategoryRepository;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.dto.ItemObjectCategoryVO;
import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;
import com.commerce.backend.service.cache.ItemObjectCategoryCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemObjectCategoryServiceImpl implements ItemObjectCategoryService {

    private final ItemObjectCategoryCacheService itemObjectCategoryCacheService;
    private final ItemObjectCategoryResponseConverter productCategoryResponseConverter;

    @Autowired
    public ItemObjectCategoryServiceImpl(ItemObjectCategoryCacheService itemObjectCategoryCacheService,
                                      ItemObjectCategoryResponseConverter productCategoryResponseConverter) {
        this.itemObjectCategoryCacheService = itemObjectCategoryCacheService;
        this.productCategoryResponseConverter = productCategoryResponseConverter;
    }


    @Override
    public List<ItemObjectCategoryResponse> findAllByOrderByName() {
        List<ItemObjectCategory> productCategories = itemObjectCategoryCacheService.findAllByOrderByName();
        if (productCategories.isEmpty()) {
            throw new ResourceNotFoundException("Could not find product categories");
        }
        return productCategories
                .stream()
                .map(productCategoryResponseConverter)
                .collect(Collectors.toList());
    }


	@Override
	public List<ItemObjectCategoryResponse> findPetsCategories() {
		List<PetCategory> petCategories = itemObjectCategoryCacheService.findPetsCategories();
		
		return petCategories
				.stream()
				.map(productCategoryResponseConverter)
				.collect(Collectors.toList());
	}


	@Override
	public List<ItemObjectCategoryResponse> findItemCategories() {
	List<ItemCategory> itemCategories = itemObjectCategoryCacheService.findItemCategories();
		
		return itemCategories
				.stream()
				.map(productCategoryResponseConverter)
				.collect(Collectors.toList());
	}


	@Override
	public ItemObjectCategoryResponse createItemObjectCategory(CategoryRequest  request) {
		ItemObjectCategory itemObjectCategory = itemObjectCategoryCacheService.createItemObjectCategory(request);
		ItemObjectCategoryResponse itemObjectResponse = new ItemObjectCategoryResponse();
		ItemObjectCategoryVO itemObjectCategoryVO = new ItemObjectCategoryVO(itemObjectCategory);
		itemObjectResponse.setCategory(itemObjectCategoryVO);
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
	
}
