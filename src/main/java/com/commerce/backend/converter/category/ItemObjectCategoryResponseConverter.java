package com.commerce.backend.converter.category;

import com.commerce.backend.model.dto.ItemObjectCategoryVO;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class ItemObjectCategoryResponseConverter implements Function<ItemObjectCategory, ItemObjectCategoryResponse> {
    @Override
    public ItemObjectCategoryResponse apply(ItemObjectCategory itemCategory) {
    	
        ItemObjectCategoryResponse itemCategoryResponse = new ItemObjectCategoryResponse();
        if(itemCategory != null) {
        itemCategoryResponse.setCategory(ItemObjectCategoryVO
        		.builder()
        		.id(itemCategory.getId())
        		.name(itemCategory.getName())
        		.type(itemCategory.getType())
        		.iconSelect(itemCategory.getIcon_select())
        		.nameAr(itemCategory.getNameAr())
        		.isExternalLink(itemCategory.getIsExternalLink())
        		.build());
        }
        return itemCategoryResponse;
    }
}
