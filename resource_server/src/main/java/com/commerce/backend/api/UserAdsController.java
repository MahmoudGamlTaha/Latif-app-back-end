package com.commerce.backend.api;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.error.exception.InvalidArgumentException;
import com.commerce.backend.model.dto.UserAdsVO;
import com.commerce.backend.model.response.product.ProductDetailsResponse;
import com.commerce.backend.service.UserAdsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<UserAdsVO>> getAll(@RequestParam("page") Integer page,
    		                                                   @RequestParam(value = "type", required= false) AdsType type,
                                                               @RequestParam(value ="size", required= false) Integer pageSize,
                                                               @RequestParam(value = "sort", required = false) String sort,
                                                               @RequestParam(value = "itemType", required = false) Long itemType,
                                                               @RequestParam(value = "category", required = false) Long category,
                                                               @RequestParam(value = "minPrice", required = false) Float minPrice,
                                                               @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                                               @RequestParam(value = "color", required = false) String color) {
        if (Objects.isNull(page) || page < 0) {
            throw new InvalidArgumentException("Invalid page");
        }
        if (Objects.isNull(pageSize) || pageSize < 0) {
               pageSize = 20;
        }
        List<UserAdsVO> userAds = userAdsService.getAll(type, page, pageSize, sort, category, minPrice, maxPrice, color);
        return new ResponseEntity<>(userAds, HttpStatus.OK);
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
    public ResponseEntity<List<UserAdsVO>> getByRelated(@PathVariable("url") String url) {
        if (url.isBlank()) {
            throw new InvalidArgumentException("Invalid url params");
        }
        List<UserAdsVO> products = userAdsService.getRelatedAds(url);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/ads/recent")
    public ResponseEntity<List<UserAdsVO>> getByNewlyAdded() {
        List<UserAdsVO> userAds = userAdsService.getNewlyAddedAds();
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
    public ResponseEntity<List<UserAdsVO>> searchProduct(@RequestParam("page") Integer page,
                                                               @RequestParam("size") Integer size,
                                                               @RequestParam("keyword") String keyword) {
        List<UserAdsVO> products = userAdsService.searchItemDisplay(keyword, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
