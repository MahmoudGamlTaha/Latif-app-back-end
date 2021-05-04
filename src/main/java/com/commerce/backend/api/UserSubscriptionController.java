package com.commerce.backend.api;

import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.model.request.subscription.UserSubscriptionRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserSubscriptionController extends PublicApiController{
    private final UserSubscriptionService service;

    @Autowired
    public UserSubscriptionController(UserSubscriptionService service) {
        this.service = service;
    }

    @GetMapping(value = "/userSubscription/getByUserId")
    public ResponseEntity<BasicResponse> getUserSubscriptionByUserId(@RequestParam(required = true) Long userId,
                                                                     @RequestParam(required = false) Optional<Integer> page,
                                                                     @RequestParam(required = false) Optional<Integer> size){
        return new ResponseEntity<>(service.findByUserId(userId, page.orElse(0), size.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @PostMapping(value = "/userSubscription/create")
    public ResponseEntity<BasicResponse> createUserSubscriptionByUserId(@RequestBody UserSubscriptionRequest request){
        return new ResponseEntity<>(service.create(request), HttpStatus.OK);
    }

    @PostMapping(value = "/userSubscription/update")
    public ResponseEntity<BasicResponse> updateUserSubscription(@RequestBody UserSubscriptionRequest request, @RequestParam(required = true) Long id){
        return new ResponseEntity<>(service.update(request, id), HttpStatus.OK);
    }

    @PostMapping(value = "/userSubscription/delete")
    public ResponseEntity<BasicResponse> deleteUserSubscription(@RequestParam(required = true) Long id){
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @GetMapping(value = "/userSubscription/getBySubscriptionType")
    public ResponseEntity<BasicResponse> getBySubscriptionType(@RequestParam(required = true) Long SubscriptionTypeId,
                                                               @RequestParam(required = false) Optional<Integer> page,
                                                               @RequestParam(required = false) Optional<Integer> size){
        return new ResponseEntity<>(service.findBySubscriptionId(SubscriptionTypeId, page.orElse(0), size.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @GetMapping(value = "/userSubscription/getById")
    public ResponseEntity<BasicResponse> getById(@RequestParam(required = true) Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }
}
