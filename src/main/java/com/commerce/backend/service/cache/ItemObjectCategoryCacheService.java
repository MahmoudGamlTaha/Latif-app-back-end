package com.commerce.backend.service.cache;

import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.request.category.CategoryUpdateRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemObjectCategoryCacheService {
    ItemObjectCategory findById(Long id);
    Page<ItemObjectCategory> findAllByOrderByName(Pageable pagable);
    BasicResponse findAllByTypeId(Integer id, Integer page);
    List<PetCategory> findPetsCategories();
    List<ItemCategory> findItemCategories();
    BasicResponse createItemObjectCategory(CategoryRequest request);
    ItemObjectCategory deleteItemObjectCategory(Long id);
    ItemObjectCategory updateItemCategory(CategoryUpdateRequest request);
    List<ItemObjectCategory> findCategoriesByParent(Long id);
}
