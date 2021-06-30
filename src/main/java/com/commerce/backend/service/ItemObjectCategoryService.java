package com.commerce.backend.service;

import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.request.category.CategoryUpdateRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ItemObjectCategoryService {
	ItemObjectCategory findById(Long id);
    BasicResponse findAllByTypeId(Integer id, Integer page);
    List<ItemObjectCategoryResponse> findAllByParent(Long parent);
	public BasicResponse findByCategoryId(Long id);
	BasicResponse findAllByOrderByName(Pageable page);
    List<ItemObjectCategoryResponse> findPetsCategories();
    List<ItemObjectCategoryResponse> findItemCategories();
    BasicResponse createItemObjectCategory(CategoryRequest request);
    ItemObjectCategoryResponse deleteItemObjectCategory(Long id);
    BasicResponse updateItemCategory(CategoryUpdateRequest request);
}
