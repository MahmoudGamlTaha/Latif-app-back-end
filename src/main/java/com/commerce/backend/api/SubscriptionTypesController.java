package com.commerce.backend.api;

import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.model.request.subscription.SubscriptionTypeRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.SubscriptionTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SubscriptionTypesController extends PublicApiController{

    private final SubscriptionTypesService subscriptionService;

    @Autowired
    public SubscriptionTypesController(SubscriptionTypesService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping(value = "/subscriptionTypes")
    public ResponseEntity<BasicResponse> getAllSubscriptionTypes(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size){
        return new ResponseEntity<>(subscriptionService.findAll(page.orElse(0), size.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @GetMapping(value = "/subscriptionTypes/getById")
    public ResponseEntity<BasicResponse> getSubscriptionTypeById(@RequestParam(required = true) Long id){
        return new ResponseEntity<>(subscriptionService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/subscriptionTypes/create")
    public ResponseEntity<BasicResponse> createSubscriptionType(@RequestBody SubscriptionTypeRequest request){
        return new ResponseEntity<>(subscriptionService.create(request), HttpStatus.OK);
    }

    @PostMapping(value = "/subscriptionTypes/update")
    public ResponseEntity<BasicResponse> deleteSubscriptionType(@RequestBody SubscriptionTypeRequest request, @RequestParam(required = true) Long id){
        return new ResponseEntity<>(subscriptionService.update(request, id), HttpStatus.OK);
    }

    @PostMapping(value = "/subscriptionTypes/delete")
    public ResponseEntity<BasicResponse> updateSubscriptionType(@RequestParam(required = true) Long id){
        return new ResponseEntity<>(subscriptionService.delete(id), HttpStatus.OK);
    }
}
