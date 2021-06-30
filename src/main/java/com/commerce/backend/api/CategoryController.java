package com.commerce.backend.api;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.request.category.CategoryUpdateRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;
import com.commerce.backend.service.ItemObjectCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(value = {"/cat-by-adType/type={adtypeId}", "/cat-by-adType/type={adtypeId}/{page}"})
    @ResponseBody
    public ResponseEntity<BasicResponse> getCategoryByAdsType(@PathVariable("adtypeId") Integer adtypeId, 
    		                                                   @PathVariable(required = false) Optional<Integer> page){
    	BasicResponse response = this.itemObjectCategoryService.findAllByTypeId(adtypeId, page.orElse(0));
    	HttpStatus status = response.getMsg() != MessageType.Success.getMessage()?HttpStatus.BAD_REQUEST: HttpStatus.OK;
    	return new ResponseEntity<BasicResponse>(response, status);
    }
    
    @GetMapping(value = {"/cat-by-parent/parent={ParentId}", "/cat-by-adType/type={adtypeId}/{page}"})
    @ResponseBody
    public ResponseEntity<BasicResponse> getCategoriesByParent(@PathVariable("adtypeId") Integer ParentId, 
    		                                                   @PathVariable(required = false) Optional<Integer> page){
    	BasicResponse response = this.itemObjectCategoryService.findAllByTypeId(ParentId, page.orElse(0));
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
    
    @GetMapping(value = {"/category/page={page}","/category"})
    public ResponseEntity<BasicResponse> getAllCategories(@PathVariable(value="page", required = false) Optional<Integer> page) {
    	Pageable pageable = PageRequest.of(page.orElse(0), SystemConstant.MOBILE_PAGE_SIZE);
         BasicResponse response = itemObjectCategoryService.findAllByOrderByName(pageable);
     	HttpStatus status = response.getMsg() != MessageType.Success.getMessage()?HttpStatus.BAD_REQUEST: HttpStatus.OK;
    	return new ResponseEntity<BasicResponse>(response, status);
    }
    @GetMapping(value = {"/category-interest/page={page}","/category-interest"})
    public ResponseEntity<BasicResponse> getInterestCategories(@PathVariable(value="page", required = false) Optional<Integer> page) {
    	Pageable pageable = PageRequest.of(page.orElse(0), SystemConstant.MOBILE_PAGE_SIZE);
         BasicResponse response = itemObjectCategoryService.findAllByOrderByName(pageable);
     	HttpStatus status = response.getMsg() != MessageType.Success.getMessage()?HttpStatus.BAD_REQUEST: HttpStatus.OK;
    	return new ResponseEntity<BasicResponse>(response, status);
    }
    @GetMapping(value = "/category/find-by-id/id={id}")
    public ResponseEntity<BasicResponse> getCategoryById(@PathVariable("id") Long id) {
        BasicResponse itemCategory = itemObjectCategoryService.findByCategoryId(id);
        return new ResponseEntity<>(itemCategory, HttpStatus.OK);
    }
    @PostMapping(value = "/category/create")
    public ResponseEntity<BasicResponse> createCategory(@RequestBody @Valid CategoryRequest request){
    	BasicResponse itemCategory = itemObjectCategoryService.createItemObjectCategory(request);
    	return new ResponseEntity<>(itemCategory, HttpStatus.OK);
    }
    @GetMapping(value = "/category/get-categories-by-parent")
     public ResponseEntity<BasicResponse> getCategoriesByParent(@RequestParam Long id){
     List<ItemObjectCategoryResponse> response = this.itemObjectCategoryService.findAllByParent(id);
     BasicResponse basicResponse = resHelper.res(response, true, MessageType.Data.getMessage(), null);
     return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    @PostMapping(value = "/category/delete")
    public ResponseEntity<ItemObjectCategoryResponse> deleteCategory(Long id){
    	ItemObjectCategoryResponse itemCategory = itemObjectCategoryService.deleteItemObjectCategory(id);
    	return new ResponseEntity<> (itemCategory, HttpStatus.OK);
    }
    @PostMapping(value = "/category/update")
    public ResponseEntity<BasicResponse> updateCategory(@RequestBody @Valid CategoryUpdateRequest request){
    	BasicResponse itemCategory = itemObjectCategoryService.updateItemCategory(request);
    	return new ResponseEntity<>(itemCategory, HttpStatus.OK);
    }
    

}
