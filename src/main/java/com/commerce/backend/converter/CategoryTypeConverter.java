package com.commerce.backend.converter;

import org.springframework.stereotype.Component;

import com.commerce.backend.model.entity.CategoryType;
import com.commerce.backend.model.response.categoryType.CategoryTypeResponse;

import java.util.function.Function;

@Component
public class CategoryTypeConverter implements Function<CategoryType, CategoryTypeResponse> {

	@Override
	public CategoryTypeResponse apply(CategoryType categoryType) {
		CategoryTypeResponse categoryTypeVO = new CategoryTypeResponse();
		if(categoryType != null) {
			categoryTypeVO.setId(categoryType.getId());
			categoryTypeVO.setActive(categoryType.getActive());
			categoryTypeVO.setName(categoryType.getName());
			categoryTypeVO.setNameAr(categoryType.getNameAr());
			categoryTypeVO.setCode(categoryType.getCode());
			categoryTypeVO.setCreatedDate(categoryType.getCreated_at());
		}
		return categoryTypeVO;
	}

}
