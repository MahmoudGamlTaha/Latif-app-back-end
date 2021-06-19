package com.commerce.backend.api;

import com.commerce.backend.model.request.role.RoleRequest;
import com.commerce.backend.model.request.role.RoleRequestUpdate;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.role.RoleResponse;
import com.commerce.backend.service.RoleServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
public class RoleController extends PublicApiController{

    private final RoleServiceImpl service;
    @Autowired
    public RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    public RoleController(RoleServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/endpoints")
    public @ResponseBody
    Object showEndpointsAction()
    {
    	
        return requestMappingHandlerMapping.getHandlerMethods().keySet().stream().map(t ->
                (t.getMethodsCondition().getMethods().size() == 0 ? "GET" : t.getMethodsCondition().getMethods().toArray()[0]) + " " +
                        t.getPatternsCondition().getPatterns().toArray()[0]
        ).toArray();
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
    public RoleResponse create(@RequestBody @Valid RoleRequest role) throws Exception {
        return service.createRole(role);
    }

    @PostMapping("/roles/update")
    public RoleResponse update(@RequestBody @Valid RoleRequestUpdate role) throws Exception {
        return service.update(role);
    }

    @PostMapping("/roles/delete")
    public BasicResponse delete(Long id)
    {
        return service.delete(id);
    }
}
