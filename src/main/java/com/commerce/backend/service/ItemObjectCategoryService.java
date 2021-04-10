package com.commerce.backend.service;

import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

import java.util.List;

public interface ItemObjectCategoryService {
    BasicResponse findAllByTypeId(Integer id);
    List<ItemObjectCategoryResponse> findAllByOrderByName();
    List<ItemObjectCategoryResponse> findPetsCategories();
    List<ItemObjectCategoryResponse> findItemCategories();
    BasicResponse createItemObjectCategory(CategoryRequest request);
    ItemObjectCategoryResponse deleteItemObjectCategory(Long id);
}
