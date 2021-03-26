package com.commerce.backend.service;

import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

import java.util.List;

public interface ItemObjectCategoryService {
    List<ItemObjectCategoryResponse> findAllByOrderByName();
    List<ItemObjectCategoryResponse> findPetsCategories();
    List<ItemObjectCategoryResponse> findItemCategories();
    ItemObjectCategoryResponse createItemObjectCategory(CategoryRequest request);
    ItemObjectCategoryResponse deleteItemObjectCategory(Long id);
}
