package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

import java.util.List;

public interface ItemObjectCategoryCacheService {
    List<ItemObjectCategory> findAllByOrderByName();
    BasicResponse findAllByTypeId(Integer id);
    List<PetCategory> findPetsCategories();
    List<ItemCategory> findItemCategories();
    ItemObjectCategory createItemObjectCategory(CategoryRequest request);
    ItemObjectCategory deleteItemObjectCategory(Long id);
}
