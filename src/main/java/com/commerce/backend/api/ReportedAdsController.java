package com.commerce.backend.api;

import com.commerce.backend.constants.ReportType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.model.request.report.ReportRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.ReportAdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ReportedAdsController extends PublicApiController{

    private final ReportAdsService reportAdsService;

    @Autowired
    public ReportedAdsController(ReportAdsService reportAdsService) {
        this.reportAdsService = reportAdsService;
    }

    @GetMapping(value = "/reportedAds/reported-ads")
    public ResponseEntity<BasicResponse> getAll(@RequestParam(value ="page", required = false) Optional<Integer> page,
                                                @RequestParam(value ="pageSize", required= false) Optional<Integer>  pageSize){
        return new ResponseEntity<>(reportAdsService.getAll(page.orElse(0), pageSize.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @GetMapping(value = "/reportedAds/interested")
    public ResponseEntity<BasicResponse> getInterestedAds(@RequestParam(value ="page", required = false) Optional<Integer> page,
                                                          @RequestParam(value ="pageSize", required= false) Optional<Integer>  pageSize){
        return new ResponseEntity<>(reportAdsService.getReportedAds(ReportType.INTEREST, page.orElse(0), pageSize.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @GetMapping(value = "/reportedAds/reportedAds")
    public ResponseEntity<BasicResponse> getReportedAds(@RequestParam(value ="page", required = false) Optional<Integer> page,
                                                          @RequestParam(value ="pageSize", required= false) Optional<Integer>  pageSize){
        return new ResponseEntity<>(reportAdsService.getReportedAds(ReportType.REPORT, page.orElse(0), pageSize.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @PostMapping(value = "/reportedAds/makeReport")
    public ResponseEntity<BasicResponse> makeReport(@RequestBody ReportRequest request){
        return new ResponseEntity<>(reportAdsService.create(request), HttpStatus.OK);
    }

    @PostMapping(value = "/reportedAds/addToInterestList")
    public ResponseEntity<BasicResponse> addToInterestList(@RequestBody ReportRequest request){
        return new ResponseEntity<>(reportAdsService.create(request), HttpStatus.OK);
    }

    @PostMapping(value = "/reportedAds/remove")
    public ResponseEntity<BasicResponse> removeFromInterestedList(@RequestParam(required = true) Long id){
        return new ResponseEntity<>(reportAdsService.removeFromInterestedList(id), HttpStatus.OK);
    }
}
