package com.commerce.backend.api;

import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestCategoryController extends PublicApiController{

    private final UserService userService;

    @Autowired
    public InterestCategoryController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/my-interest-categories")
    public ResponseEntity<BasicResponse> getUserInterestCategories() {
        return new ResponseEntity<>(userService.getUserInterestCategories(), HttpStatus.OK);
    }

    @PostMapping(value = "/interest-categories/create")
    public ResponseEntity<BasicResponse> createInterestCategories(Long categoryId) {
        return new ResponseEntity<>(userService.createInterestCategories(categoryId), HttpStatus.OK);
    }

    @PostMapping(value = "/interest-categories/remove")
    public ResponseEntity<BasicResponse> removeInterestCategories(Long categoryId) {
        return new ResponseEntity<>(userService.removeInterestCategories(categoryId), HttpStatus.OK);
    }

    @PostMapping(value = "/users-interest-categories")
    public ResponseEntity<BasicResponse> getUsersInterestCategories(Long categoryId) {
        return new ResponseEntity<>(userService.getUsersInterestCategories(), HttpStatus.OK);
    }
}
