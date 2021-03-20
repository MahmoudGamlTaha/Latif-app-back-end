package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.request.category.CategoryRequest;

import java.util.List;

public interface ItemObjectCategoryCacheService {
    List<ItemObjectCategory> findAllByOrderByName();
    List<PetCategory> findPetsCategories();
    List<ItemCategory> findItemCategories();
    ItemObjectCategory createItemObjectCategory(CategoryRequest request);
    ItemObjectCategory deleteItemObjectCategory(Long id);
}
