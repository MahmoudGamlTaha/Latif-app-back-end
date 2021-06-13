package com.commerce.backend.api;

import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.ReportReasonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportReasonsController  extends PublicApiController{

    private final ReportReasonsService reportReasonsService;

    @Autowired
    public ReportReasonsController(ReportReasonsService reportReasonsService) {
        this.reportReasonsService = reportReasonsService;
    }

    @GetMapping("/reasons")
    public ResponseEntity<BasicResponse> reasons(){
        return new ResponseEntity<>(reportReasonsService.getReasons(), HttpStatus.OK);
    }

    @PostMapping("/reasons/create")
    public ResponseEntity<BasicResponse> createReasons(@RequestParam(name = "reason") String reason, @RequestParam(name = "reasonAr", required = false) String reasonAr){
        return new ResponseEntity<>(reportReasonsService.createReason(reason, reasonAr), HttpStatus.OK);
    }

    @PostMapping("/reasons/update")
    public ResponseEntity<BasicResponse> updateReasons(@RequestParam(name = "id") Long id, @RequestParam(name = "reason") String reason, @RequestParam(name = "reasonAr", required =false) String reasonAr){
        return new ResponseEntity<>(reportReasonsService.updateReason(id, reason, reasonAr), HttpStatus.OK);
    }

    @PostMapping("/reasons/remove")
    public ResponseEntity<BasicResponse> removeReasons(@RequestParam(name = "id") Long id){
        return new ResponseEntity<>(reportReasonsService.removeReason(id), HttpStatus.OK);
    }
}
