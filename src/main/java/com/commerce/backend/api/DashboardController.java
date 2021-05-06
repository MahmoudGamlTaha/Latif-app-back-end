package com.commerce.backend.api;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController extends PublicApiController{

    private final DashboardService service;

    @Autowired
    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping(value = "/dashboard/dashboard")
    public ResponseEntity<BasicResponse> getDashboard()
    {
        return new ResponseEntity<BasicResponse>(service.getDashboard(), HttpStatus.OK);
    }

    @GetMapping(value = "/dashboard/AdsCount")
    public ResponseEntity<BasicResponse> getAdsCountByCategoryId(@RequestParam(required = true) Long CategoryId)
    {
        return new ResponseEntity<BasicResponse>(service.getAdsCountByCategoryId(CategoryId), HttpStatus.OK);
    }
}
