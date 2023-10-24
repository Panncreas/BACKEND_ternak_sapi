package com.ternak.sapi.controller;

import com.ternak.sapi.model.DataTernak;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.dataTernak.DataTernakRequest;
import com.ternak.sapi.payload.dataTernak.DataTernakResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.DataTernakService;
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
import com.ternak.sapi.repository.DataTernakRepository;

@RestController
@RequestMapping("/api/data-ternak")
public class DataTernakController {
    @Autowired
    private DataTernakRepository dataTernakRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataTernakService dataTernakService;

    private static final Logger logger = LoggerFactory.getLogger(DataTernakController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<DataTernakResponse> getDataTernak(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return dataTernakService.getAllDataTernak(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createDataTernak(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody DataTernakRequest dataTernakRequest) {
        DataTernak dataTernak = dataTernakService.createDataTernak(currentUser, dataTernakRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{dataTernakId}")
                .buildAndExpand(dataTernak.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "DataTernak Created Successfully"));
    }

    @PutMapping("/{dataTernakId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateDataTernakById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "dataTernakId") String dataTernakId, @Valid @RequestBody DataTernakRequest dataTernakRequest) {
        DataTernak dataTernak = dataTernakService.updateDataTernak(dataTernakRequest, dataTernakId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{dataTernakId}")
                .buildAndExpand(dataTernak.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "DataTernak Updated Successfully"));
    }

    @GetMapping("/{dataTernakId}")
    @Secured("ROLE_ADMINISTRATOR")
    public DataTernakResponse getDataTernakById(@PathVariable String dataTernakId) {
        return dataTernakService.getDataTernakById(dataTernakId);
    }

    @DeleteMapping("/{dataTernakId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteDataTernak(@PathVariable (value = "dataTernakId") String dataTernakId){
        dataTernakService.deleteDataTernakById(dataTernakId);
        return HttpStatus.FORBIDDEN;
    }
}
