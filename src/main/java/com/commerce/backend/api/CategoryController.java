package com.commerce.backend.api;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;
import com.commerce.backend.service.ItemObjectCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
public class CategoryController extends PublicApiController {

    private final ItemObjectCategoryService itemObjectCategoryService;
    private static final Logger logger2 = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    public CategoryController(ItemObjectCategoryService itemObjectCategoryService) {
        this.itemObjectCategoryService = itemObjectCategoryService;
    }

    @GetMapping(value = "/cat-by-adType/type={adtypeId}/{page}")
    @ResponseBody
    public ResponseEntity<BasicResponse> getCategoryByAdsType(@PathVariable("adtypeId") Integer adtypeId, 
    		                                                   @PathVariable(name="page",required = false) Optional<Integer> page){
    	logger2.info("======path variable=========:"+ adtypeId);
    	BasicResponse response = this.itemObjectCategoryService.findAllByTypeId(adtypeId, page.orElse(0));
    	HttpStatus status = response.getMsg() != MessageType.Success.getMessage()?HttpStatus.BAD_REQUEST: HttpStatus.OK;
    	return new ResponseEntity<BasicResponse>(response, status);
    }
    @GetMapping(value = "/pet-category")
    public ResponseEntity<List<ItemObjectCategoryResponse>> getPetCategories() {
        List<ItemObjectCategoryResponse> productCategories = itemObjectCategoryService.findPetsCategories();
        return new ResponseEntity<>(productCategories, HttpStatus.OK);
    }
    
    @GetMapping(value = "/acc-category")
    public ResponseEntity<List<ItemObjectCategoryResponse>> getAccCategories() {
        List<ItemObjectCategoryResponse> accCategories = itemObjectCategoryService.findItemCategories();
        return new ResponseEntity<>(accCategories, HttpStatus.OK);
    }
    
    @GetMapping(value = "/category")
    public ResponseEntity<List<ItemObjectCategoryResponse>> getAllCategories() {
        List<ItemObjectCategoryResponse> itemCategories = itemObjectCategoryService.findAllByOrderByName();
        return new ResponseEntity<>(itemCategories, HttpStatus.OK);
    }
    
    @PostMapping(value = "/category/create")
    public ResponseEntity<ItemObjectCategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request){
    	ItemObjectCategoryResponse itemCategory = itemObjectCategoryService.createItemObjectCategory(request);
    	return new ResponseEntity<>(itemCategory, HttpStatus.OK);
    }
    
    @PostMapping(value = "/category/delete")
    public ResponseEntity<ItemObjectCategoryResponse> deleteCategory(Long id){
    	ItemObjectCategoryResponse itemCategory = itemObjectCategoryService.deleteItemObjectCategory(id);
    	return new ResponseEntity<> (itemCategory, HttpStatus.OK);
    }
    

}
