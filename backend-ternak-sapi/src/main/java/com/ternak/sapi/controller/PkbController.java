package com.ternak.sapi.controller;

import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.pkb.PkbRequest;
import com.ternak.sapi.payload.pkb.PkbResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.PkbService;
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
import com.ternak.sapi.repository.PkbRepository;

@RestController
@RequestMapping("/api/pkb")
public class PkbController {
    @Autowired
    private PkbRepository pkbRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PkbService pkbService;

    private static final Logger logger = LoggerFactory.getLogger(PkbController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<PkbResponse> getPkb(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pkbService.getAllPkb(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createPkb(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PkbRequest pkbRequest) {
        Pkb pkb = pkbService.createPkb(currentUser, pkbRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pkbId}")
                .buildAndExpand(pkb.getIdKejadian()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pkb Created Successfully"));
    }

    @PutMapping("/{pkbId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updatePkbById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "pkbId") String pkbId, @Valid @RequestBody PkbRequest pkbRequest) {
        Pkb pkb = pkbService.updatePkb(pkbRequest, pkbId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pkbId}")
                .buildAndExpand(pkb.getIdKejadian()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pkb Updated Successfully"));
    }

    @GetMapping("/{pkbId}")
    @Secured("ROLE_ADMINISTRATOR")
    public PkbResponse getPkbById(@PathVariable String pkbId) {
        return pkbService.getPkbById(pkbId);
    }

    @DeleteMapping("/{pkbId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deletePkb(@PathVariable (value = "pkbId") String pkbId){
        pkbService.deletePkbById(pkbId);
        return HttpStatus.FORBIDDEN;
    }
}
