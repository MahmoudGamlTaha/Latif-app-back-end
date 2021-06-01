package com.commerce.backend.api;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.request.userAds.*;
import com.commerce.backend.model.request.userAds.AdsFiltrationRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import com.commerce.backend.service.UserAdsService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@RestController
public class UserAdsController extends PublicApiController {

    private final UserAdsService userAdsService;


    @Autowired
    public UserAdsController(UserAdsService userAdsService) {
        this.userAdsService = userAdsService;
    }
    

    @GetMapping(value = "/ads")
    @ResponseBody
    public ResponseEntity<BasicResponse> getAll(@RequestParam(value ="page", required = false) Optional<Integer> page,
    											@RequestParam(value ="longitude") double longitude,
    											@RequestParam(value ="latitude") double latitude,
    		                                                   @RequestParam(value = "type", required= true) AdsType type,
                                                               @RequestParam(value ="size", required= false) Optional<Integer> pageSize,
                                                               @RequestParam(value = "sort", required = false) String sort,
                                                               @RequestParam(value = "category", required = false) Long category,
                                                               @RequestParam(value = "minPrice", required = false) Float minPrice,
                                                               @RequestParam(value = "maxPrice", required = false) Float maxPrice) {
    	LocationRequest location = null; 
    	if(longitude!= 0) {
    		location = new LocationRequest();
    		location.setLatitude(latitude);
    		location.setLongitude(longitude);
    	}
        BasicResponse response = userAdsService.getAll(type, location, page.orElse(0), pageSize.orElse(SystemConstant.MOBILE_PAGE_SIZE), sort, category, minPrice, maxPrice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @GetMapping("/ads/ad-by-Id")
    public ResponseEntity<BasicResponse> getAdById(@RequestParam(value = "id", required = true) Long id) throws Exception {
        BasicResponse res = userAdsService.findAdsById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping(value = "/ads/count")
    public ResponseEntity<Long> getAllCount(@RequestParam(value = "category", required = false) String category,
                                            @RequestParam(value = "minPrice", required = false) Float minPrice,
                                            @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                            @RequestParam(value = "color", required = false) String color) {
        Long productCount = userAdsService.getAllCount(category, minPrice, maxPrice, color);
        return new ResponseEntity<>(productCount, HttpStatus.OK);
    }
    
    @PostMapping(value = "/ads/ads-filtration")
    public ResponseEntity<BasicResponse> adsFiltration(@RequestBody AdsFiltrationRequest<String, Object> filterRequest) {
    	Pageable pageable = PageRequest.of(0, SystemConstant.MOBILE_PAGE_SIZE);
        return new ResponseEntity<BasicResponse>(userAdsService.adsFiltration(filterRequest, pageable), HttpStatus.OK);
    }
    
    @CrossOrigin(origins = "*")   
    @GetMapping(value = "/ads/nearest")
    @ResponseBody
    public ResponseEntity<BasicResponse> getNearest(@RequestParam(value ="longitude", required = false) Optional<Double> longitude,
                                                      @RequestParam(value ="latitude", required = false) Optional<Double> latitude,
                                                      @RequestParam(value ="distance", required = false) Integer distance,
                                                      @RequestParam(value ="page", required = false) Optional<Integer> page,
                                                      @RequestParam(value ="pageSize", required= false) Optional<Integer>  pageSize,
                                                      @RequestParam(value = "sort", required = false) String sort,
                                                      @RequestParam(value = "category", required = false) Long category,
                                                      @RequestParam(value = "type", required= false) Optional<AdsType> type)
                                                  
    {
    	distance = distance == null? SystemConstant.DISTANCE: distance;
    	
        BasicResponse res = userAdsService.findNearby(type.orElse(AdsType.ALL),longitude.orElse(null), latitude.orElse(null), distance, page.orElse(0), pageSize.orElse(SystemConstant.MOBILE_PAGE_SIZE), category);
        return new ResponseEntity<>(res, HttpStatus.OK);
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
    public ResponseEntity<BasicResponse> uploadAdsImage(){
    	return new ResponseEntity<BasicResponse>(new BasicResponse(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/ads/create")
    public ResponseEntity<BasicResponse> createUserAds(@RequestBody DynamicAdsRequest<String, Object> userAdsRequest){
    	BasicResponse response = null;
    	HttpStatus status = HttpStatus.OK;
    	try {
    	     response = this.userAdsService.createUserAds(userAdsRequest, null, null, userAdsRequest.getExternal());
    	}catch(Exception ex) {
    		response = new BasicResponse();
    		response.setMsg(ex.getMessage());
    		response.setSuccess(false);
    		status = HttpStatus.BAD_REQUEST;
    	}
    	 return new ResponseEntity<BasicResponse>(response, status); 
    }

    @GetMapping(value = "ads/myAds")
    public ResponseEntity<BasicResponse> myAds(@RequestParam(value ="page", required = false) Optional<Integer> page,
                                               @RequestParam(value ="pageSize", required= false) Optional<Integer>  pageSize){
        return new ResponseEntity<>(userAdsService.myAds(page.orElse(0), pageSize.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @PostMapping(value = "/ads/update")
    public ResponseEntity<BasicResponse> updateUserAds(@RequestBody UpdateAdRequest<String, Object> request, @RequestParam(required = false) List<String> fileList, @RequestParam(required = false) List<MultipartFile> files) {
        return new ResponseEntity<BasicResponse>(userAdsService.updateUserAds(request, fileList, files), HttpStatus.OK);
    }
    
    @GetMapping(value = "/ads/get-create-form")
    @ResponseBody
    public ResponseEntity<BasicResponse> getCreateForm(@RequestParam(value = "adType",required = true) AdsType adType, @RequestParam(value = "category",required = false) Optional<Long> category) throws Exception {
    	adTypeRequest adRequest = new adTypeRequest();
     	adRequest.setAdsType(adType);	
    	return new ResponseEntity<BasicResponse>(userAdsService.getCreateForm(adRequest, category.orElse(null)), HttpStatus.OK);
    }
    
    @GetMapping(value = "/ads/get-filter-form")
    @ResponseBody
    public ResponseEntity<BasicResponse> getFilterForm(@RequestParam(value = "adType",required = true) AdsType adType, @RequestParam(value = "category",required = false) Optional<Long> category) throws Exception {
    	adTypeRequest adRequest = new adTypeRequest();
    	adRequest.setAdsType(adType);
    	
    	return new ResponseEntity<BasicResponse>(userAdsService.getFilterForm(adRequest, category.orElse(null)), HttpStatus.OK);
    }
    
    /* @GetMapping(value = "/ads/{url}")
    public ResponseEntity<ProductDetailsResponse> getByUrl(@PathVariable("url") String url) {
        if (url.isBlank()) {
            throw new InvalidArgumentException("Invalid url params");
        }
        ProductDetailsResponse productDetailsResponse = userAdsService.findByUrl(url);
        return new ResponseEntity<>(productDetailsResponse, HttpStatus.OK);
    }

   /* @GetMapping(value = "/ads/related/{url}")
    public ResponseEntity<List<UserAdsVO>> getByRelated(UserAdsVO userAds) {
        if (userAds == null) {
            throw new InvalidArgumentException("Invalid  params");
        }
        List<UserAdsVO> userAdsVO = userAdsService.getRelatedAds(userAds);
        return new ResponseEntity<>(userAdsVO, HttpStatus.OK);
    }

   /* @GetMapping(value = "/ads/recent")
    public ResponseEntity<List<UserAdsVO>> getByNewlyAdded(@RequestParam(value ="ads_type", required = false)  AdsType type,  @RequestParam(value="cat_id", required = false) Long cat_id) {
        List<UserAdsVO> userAds = userAdsService.getNewlyAddedAds(type, cat_id);
        return new ResponseEntity<>(userAds, HttpStatus.OK);
    }*/

    @PostMapping("/ads/adActivation")
    public ResponseEntity<BasicResponse> adActivation(@RequestParam(value = "id", required = true) Long id, @RequestParam(value = "activate", required = true) boolean activate) throws Exception {
        BasicResponse res = userAdsService.adActivation(id, activate);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
