package com.commerce.backend.api;

import com.commerce.backend.model.request.AssignUserPermissionRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.AssignUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AssignUserPermissionController extends PublicApiController{

    private final AssignUserPermissionService assignUserPermissionService;

    @Autowired
    public AssignUserPermissionController(AssignUserPermissionService assignUserPermissionService) {
        this.assignUserPermissionService = assignUserPermissionService;
    }

    @GetMapping("/assignUserPermission/getByRoleId")
    public ResponseEntity<BasicResponse> getByRoleId(@RequestParam Long roleId){
        return new ResponseEntity<>(assignUserPermissionService.getAll(roleId), HttpStatus.OK);
    }

    @PostMapping("/assignUserPermission/create")
    public ResponseEntity<BasicResponse> create(@RequestBody AssignUserPermissionRequest assignUserPermission){
        return new ResponseEntity<>(assignUserPermissionService.create(assignUserPermission), HttpStatus.OK);
    }

    @PostMapping("/assignUserPermission/remove")
    public ResponseEntity<BasicResponse> remove(@RequestBody AssignUserPermissionRequest assignUserPermission){
        return new ResponseEntity<>(assignUserPermissionService.remove(assignUserPermission), HttpStatus.OK);
    }
}
