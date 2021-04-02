package com.commerce.backend.api;

import com.commerce.backend.model.request.category.CategoryTypeRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.CategoryTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CategoryTypeAdsController extends PublicApiController{
    
	@Autowired
    private final CategoryTypeService service;

    public CategoryTypeAdsController(CategoryTypeService service) {
        this.service = service;
    }

    @GetMapping("/ads-type/list")
    public ResponseEntity<BasicResponse> getAdsType(){
        return service.getCategoryTypes();
    }

    @GetMapping("/ads-type/types")
    @ResponseBody
    public ResponseEntity<BasicResponse> getCategoryById(@RequestParam Long id)
    {
        return service.getCategoryTypeById(id);
    }

    @PostMapping("/ads-type/create")
    public ResponseEntity<BasicResponse> create(@ModelAttribute @Valid CategoryTypeRequest categoryRequest) throws Exception {
        return service.saveCategoryType(categoryRequest);
    }

    @PostMapping("/ads-type/update")
    public ResponseEntity<BasicResponse> update(@ModelAttribute @Valid CategoryTypeRequest request) throws Exception {
        return service.updateCategoryType(request);
    }

    @PostMapping("/ads-type/delete")
    public ResponseEntity<BasicResponse> delete(Long id)
    {
        return service.deleteCategoryType(id);
    }
}
