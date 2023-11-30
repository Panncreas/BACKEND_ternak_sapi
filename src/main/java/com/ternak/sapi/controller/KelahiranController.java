package com.ternak.sapi.controller;

import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.kelahiran.KelahiranRequest;
import com.ternak.sapi.payload.kelahiran.KelahiranResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.KelahiranService;
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
import com.ternak.sapi.repository.KelahiranRepository;

@RestController
@RequestMapping("/api/kelahiran")
public class KelahiranController {
    @Autowired
    private KelahiranRepository kelahiranRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KelahiranService kelahiranService;

    private static final Logger logger = LoggerFactory.getLogger(KelahiranController.class);

    @GetMapping
    @Secured({"ROLE_ADMINISTRATOR" , "ROLE_LECTURE"})
    public PagedResponse<KelahiranResponse> getKelahiran(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return kelahiranService.getAllKelahiran(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createKelahiran(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody KelahiranRequest kelahiranRequest) {
        Kelahiran kelahiran = kelahiranService.createKelahiran(currentUser, kelahiranRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{kelahiranId}")
                .buildAndExpand(kelahiran.getIdKejadian()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Kelahiran Created Successfully"));
    }

    @PutMapping("/{kelahiranId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateKelahiranById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "kelahiranId") String kelahiranId, @Valid @RequestBody KelahiranRequest kelahiranRequest) {
        Kelahiran kelahiran = kelahiranService.updateKelahiran(kelahiranRequest, kelahiranId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{kelahiranId}")
                .buildAndExpand(kelahiran.getIdKejadian()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Kelahiran Updated Successfully"));
    }

    @GetMapping("/{kelahiranId}")
    @Secured("ROLE_ADMINISTRATOR")
    public KelahiranResponse getKelahiranById(@PathVariable String kelahiranId) {
        return kelahiranService.getKelahiranById(kelahiranId);
    }

    @DeleteMapping("/{kelahiranId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteKelahiran(@PathVariable (value = "kelahiranId") String kelahiranId){
        kelahiranService.deleteKelahiranById(kelahiranId);
        return HttpStatus.FORBIDDEN;
    }
}
