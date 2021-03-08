package com.commerce.backend.api;

import com.commerce.backend.model.request.role.RoleRequest;
import com.commerce.backend.model.request.role.RoleRequestUpdate;
import com.commerce.backend.model.response.role.RoleResponse;
import com.commerce.backend.service.RoleServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoleController extends PublicApiController{

    private final RoleServiceImpl service;

    public RoleController(RoleServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/roles")
    public List<RoleResponse> getRoles()
    {
        return service.getRolesList();
    }

    @GetMapping("/roles/id={id}")
    public RoleResponse getRoleById(@PathVariable Long id)
    {
        return service.getRoleById(id);
    }

    @PostMapping("/roles/create")
    public RoleResponse create(@ModelAttribute @Valid RoleRequest role) throws Exception {
        return service.createRole(role);
    }

    @PostMapping("/roles/update")
    public RoleResponse update(@ModelAttribute @Valid RoleRequestUpdate role) throws Exception {
        return service.update(role);
    }

    @PostMapping("/roles/delete")
    public String delete(Long id)
    {
        return service.delete(id);
    }
}
