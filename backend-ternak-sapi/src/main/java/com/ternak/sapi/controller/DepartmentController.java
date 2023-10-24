package com.ternak.sapi.controller;

import com.ternak.sapi.model.Department;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.department.DepartmentRequest;
import com.ternak.sapi.payload.department.DepartmentResponse;
import com.ternak.sapi.repository.DepartmentRepository;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.DepartmentService;
import com.ternak.sapi.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentService departmentService;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<DepartmentResponse> getDepartment(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return departmentService.getAllDepartment(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createDepartment(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody DepartmentRequest departmentRequest) {
        Department department = departmentService.createDepartment(currentUser, departmentRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{departmentId}")
                .buildAndExpand(department.getKodeEartagNasional()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Department Created Successfully"));
    }

    @PutMapping("/{departmentId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateDepartmentById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "departmentId") String departmentId, @Valid @RequestBody DepartmentRequest departmentRequest) {
        Department department = departmentService.updateDepartment(departmentRequest, departmentId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{departmentId}")
                .buildAndExpand(department.getKodeEartagNasional()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Department Updated Successfully"));
    }

    @GetMapping("/{departmentId}")
    @Secured("ROLE_ADMINISTRATOR")
    public DepartmentResponse getDepartmentById(@PathVariable String departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @DeleteMapping("/{departmentId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteDepartment(@PathVariable (value = "departmentId") String departmentId){
        departmentService.deleteDepartmentById(departmentId);
        return HttpStatus.FORBIDDEN;
    }
}
