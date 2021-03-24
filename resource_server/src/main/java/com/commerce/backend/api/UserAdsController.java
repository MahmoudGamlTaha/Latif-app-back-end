package com.commerce.backend.api;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.model.dto.UserAccVO;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.dto.UserPetAdsVO;
import com.commerce.backend.model.request.userAds.*;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import com.commerce.backend.service.UserAdsService;


import com.commerce.backend.service.UserAdsServiceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class UserAdsController extends PublicApiController {

    private final UserAdsService userAdsService;


    @Autowired
    public UserAdsController(UserAdsService userAdsService) {
        this.userAdsService = userAdsService;
    }


    @GetMapping(value = "/ads")
    public ResponseEntity<BasicResponse> getAll(@RequestParam(value ="page", required = false) Integer page,
    		                                                   @RequestParam(value = "type", required= false) AdsType type,
                                                               @RequestParam(value ="size", required= false) Integer pageSize,
                                                               @RequestParam(value = "sort", required = false) String sort,
                                                               @RequestParam(value = "itemType", required = false) Long itemType,
                                                               @RequestParam(value = "category", required = false) Long category,
                                                               @RequestParam(value = "minPrice", required = false) Float minPrice,
                                                               @RequestParam(value = "maxPrice", required = false) Float maxPrice) {
        if (Objects.isNull(page) || page < 0) {
          page = 1;
        }
        if (Objects.isNull(pageSize) || pageSize < 0) {
               pageSize = 20;
        }
        BasicResponse response = userAdsService.getAll(type, page, pageSize, sort, category, minPrice, maxPrice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/ads/count")
    public ResponseEntity<Long> getAllCount(@RequestParam(value = "category", required = false) String category,
                                            @RequestParam(value = "minPrice", required = false) Float minPrice,
                                            @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                            @RequestParam(value = "color", required = false) String color) {
        Long productCount = userAdsService.getAllCount(category, minPrice, maxPrice, color);
        return new ResponseEntity<>(productCount, HttpStatus.OK);
    }

    @GetMapping(value = "/ads/{url}")
    public ResponseEntity<ProductDetailsResponse> getByUrl(@PathVariable("url") String url) {
        if (url.isBlank()) {
            throw new InvalidArgumentException("Invalid url params");
        }
        ProductDetailsResponse productDetailsResponse = userAdsService.findByUrl(url);
        return new ResponseEntity<>(productDetailsResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/ads/related/{url}")
    public ResponseEntity<List<UserAdsVO>> getByRelated(UserAdsVO userAds) {
        if (userAds == null) {
            throw new InvalidArgumentException("Invalid  params");
        }
        List<UserAdsVO> userAdsVO = userAdsService.getRelatedAds(userAds);
        return new ResponseEntity<>(userAdsVO, HttpStatus.OK);
    }

    @GetMapping(value = "/ads/recent")
    public ResponseEntity<List<UserAdsVO>> getByNewlyAdded(@RequestParam(value ="ads_type", required = false)  AdsType type,  @RequestParam(value="cat_id", required = false) Long cat_id) {
        List<UserAdsVO> userAds = userAdsService.getNewlyAddedAds(type, cat_id);
        return new ResponseEntity<>(userAds, HttpStatus.OK);
    }

 
    @GetMapping(value = "/ads/interested")
    public ResponseEntity<List<UserAdsVO>> getByInterested(@RequestParam("token") String token,
    														@RequestParam("userId") Long userId) {
        List<UserAdsVO> userAdsVO = userAdsService.getInterested(userId, token);
        return new ResponseEntity<>(userAdsVO, HttpStatus.OK);
    }
    public ResponseEntity<List<UserAdsVO>> makeItemIntersted(@RequestParam("item_id")Long itemId,
    		                                   @RequestParam("token")String token){
    	return null;
    	
    }

    @GetMapping(value = "/ads/search")
    public ResponseEntity<List<UserAdsVO>> searchAds(@RequestParam("page") Integer page,
                                                               @RequestParam("size") Integer size,
                                                               @RequestParam("keyword") String keyword) {
        List<UserAdsVO> products = userAdsService.searchItemDisplay(keyword, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(value = "/ads/create")
    public ResponseEntity<BasicResponse> createUserAds(@RequestBody @Valid DynamicAdsRequest<String, String> userAdsRequest,
                                                       @RequestParam(value = "file", required = false) MultipartFile file){
    	
    	BasicResponse response = this.userAdsService.createUserAds(userAdsRequest, file);
    	return new ResponseEntity<BasicResponse>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/ads/food/create")
    public ResponseEntity<UserAdsVO> createFoodAds(@RequestBody UserPetsAdsRequest userPetsAdsRequest){
    	
    	return null;
    }
    @PostMapping(value = "/ads/service/create")
    public ResponseEntity<UserAdsVO> createrServiceAds(@RequestBody UserPetsAdsRequest userPetsAdsRequest){
    	
    	return null;
    }

    @GetMapping(value = "/ads/pet/create")
    public JSONObject petResponse(@RequestBody(required = false) @Valid PetTypeRequest petType) throws Exception {
        return userAdsService.getPetsResponse(petType);
    }

    @GetMapping(value = "/ads/form/")
    public ResponseEntity<String> getCreateAdsForm(AdsType form){
      if(form == AdsType.PETS) {
    	  return new ResponseEntity<String>(new UserPetAdsVO().toString(), HttpStatus.OK);
      }
      if(form == AdsType.ACCESSORIES) {
          return new ResponseEntity<String>(new UserAccVO().toString(), HttpStatus.OK);
      }
      if(form == AdsType.SERVICE) {

      }
      if(form == AdsType.PET_CARE) {

      }

    	return null;
    }
    
    @GetMapping(value = "/ads/type")
    @ResponseBody
     public ResponseEntity<List<AdsType>> getAdsType(){
    	getLogger().info("array {}", (Object)Arrays.asList( AdsType.class.getEnumConstants()));
    
        return new ResponseEntity<>(Arrays.asList( AdsType.class.getEnumConstants()), HttpStatus.OK) ;	
    }
}