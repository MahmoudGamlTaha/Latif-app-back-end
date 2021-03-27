package com.commerce.backend.service;



import com.commerce.backend.model.request.category.CategoryTypeRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.categoryType.CategoryTypeResponse;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.List;

public interface CategoryTypeService {
	ResponseEntity<BasicResponse> getCategoryTypes();
	ResponseEntity<BasicResponse> getCategoryTypeById(Long id);
    List<CategoryTypeResponse> search(String keyword);
    ResponseEntity<BasicResponse> saveCategoryType(CategoryTypeRequest blog);
    ResponseEntity<BasicResponse> deleteCategoryType(Long id);
    ResponseEntity<BasicResponse> updateCategoryType(CategoryTypeRequest request) throws IOException;
}
