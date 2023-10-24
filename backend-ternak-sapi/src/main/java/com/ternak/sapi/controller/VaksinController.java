package com.ternak.sapi.controller;

import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.vaksin.VaksinRequest;
import com.ternak.sapi.payload.vaksin.VaksinResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.VaksinService;
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
import com.ternak.sapi.repository.VaksinRepository;

@RestController
@RequestMapping("/api/vaksin")
public class VaksinController {
    @Autowired
    private VaksinRepository vaksinRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VaksinService vaksinService;

    private static final Logger logger = LoggerFactory.getLogger(VaksinController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<VaksinResponse> getVaksin(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return vaksinService.getAllVaksin(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createVaksin(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody VaksinRequest vaksinRequest) {
        Vaksin vaksin = vaksinService.createVaksin(currentUser, vaksinRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{vaksinId}")
                .buildAndExpand(vaksin.getIdVaksin()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Vaksin Created Successfully"));
    }

    @PutMapping("/{vaksinId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateVaksinById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "vaksinId") String vaksinId, @Valid @RequestBody VaksinRequest vaksinRequest) {
        Vaksin vaksin = vaksinService.updateVaksin(vaksinRequest, vaksinId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{vaksinId}")
                .buildAndExpand(vaksin.getIdVaksin()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Vaksin Updated Successfully"));
    }

    @GetMapping("/{vaksinId}")
    @Secured("ROLE_ADMINISTRATOR")
    public VaksinResponse getVaksinById(@PathVariable String vaksinId) {
        return vaksinService.getVaksinById(vaksinId);
    }

    @DeleteMapping("/{vaksinId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteVaksin(@PathVariable (value = "vaksinId") String vaksinId){
        vaksinService.deleteVaksinById(vaksinId);
        return HttpStatus.FORBIDDEN;
    }
}
