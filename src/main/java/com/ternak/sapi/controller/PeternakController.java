package com.ternak.sapi.controller;

import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.peternak.PeternakRequest;
import com.ternak.sapi.payload.peternak.PeternakResponse;
import com.ternak.sapi.repository.PeternakRepository;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.PeternakService;
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
@RequestMapping("/api/peternak")
public class PeternakController {
    @Autowired
    private PeternakRepository peternakRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PeternakService peternakService;

    private static final Logger logger = LoggerFactory.getLogger(PeternakController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<PeternakResponse> getPeternak(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return peternakService.getAllPeternak(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createPeternak(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PeternakRequest peternakRequest) {
        Peternak peternak = peternakService.createPeternak(currentUser, peternakRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{peternakId}")
                .buildAndExpand(peternak.getIdPeternak()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Peternak Created Successfully"));
    }

    @PutMapping("/{peternakId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updatePeternakById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "peternakId") String peternakId, @Valid @RequestBody PeternakRequest peternakRequest) {
        Peternak peternak = peternakService.updatePeternak(peternakRequest, peternakId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{peternakId}")
                .buildAndExpand(peternak.getIdPeternak()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Peternak Updated Successfully"));
    }

    @GetMapping("/{peternakId}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_PETERNAK"})
    public PeternakResponse getPeternakById(@PathVariable String peternakId) {
        return peternakService.getPeternakById(peternakId);
    }

    @DeleteMapping("/{peternakId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deletePeternak(@PathVariable (value = "peternakId") String peternakId){
        peternakService.deletePeternakById(peternakId);
        return HttpStatus.FORBIDDEN;
    }
}
