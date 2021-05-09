package com.commerce.backend.service;

import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.request.category.CategoryUpdateRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

import java.util.List;

public interface ItemObjectCategoryService {
	ItemObjectCategory findById(Long id);
    BasicResponse findAllByTypeId(Integer id, Integer page);
	public BasicResponse findByCategoryId(Long id);
    List<ItemObjectCategoryResponse> findAllByOrderByName();
    List<ItemObjectCategoryResponse> findPetsCategories();
    List<ItemObjectCategoryResponse> findItemCategories();
    BasicResponse createItemObjectCategory(CategoryRequest request);
    ItemObjectCategoryResponse deleteItemObjectCategory(Long id);
    BasicResponse updateItemCategory(CategoryUpdateRequest request);
}
