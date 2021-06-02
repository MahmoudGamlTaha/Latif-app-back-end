package com.commerce.backend.api;

import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.model.request.userPermission.UpdateUserPermissionRequest;
import com.commerce.backend.model.request.userPermission.UserPermissionRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserPermissionController extends PublicApiController{

    private final UserPermissionService userPermissionService;

    @Autowired
    public UserPermissionController(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }


    @GetMapping("/userPermission")
    public ResponseEntity<BasicResponse> getAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize){
        return new ResponseEntity<>(userPermissionService.getAll(page.orElse(0), pageSize.orElse(SystemConstant.MOBILE_PAGE_SIZE)), HttpStatus.OK);
    }

    @PostMapping("/userPermission/create")
    public ResponseEntity<BasicResponse> create(@RequestBody List<UserPermissionRequest> userPermission){
        return new ResponseEntity<>(userPermissionService.create(userPermission), HttpStatus.OK);
    }

    @PostMapping("/userPermission/update")
    public ResponseEntity<BasicResponse> update(@RequestBody List<UpdateUserPermissionRequest> userPermission){
        return new ResponseEntity<>(userPermissionService.update(userPermission), HttpStatus.OK);
    }

    @PostMapping("/userPermission/remove")
    public ResponseEntity<BasicResponse> remove(@RequestParam Long id){
        return new ResponseEntity<>(userPermissionService.remove(id), HttpStatus.OK);
    }
}
