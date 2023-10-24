package com.ternak.sapi.controller;

import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.inseminasi.InseminasiRequest;
import com.ternak.sapi.payload.inseminasi.InseminasiResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.InseminasiService;
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
import com.ternak.sapi.repository.InseminasiRepository;

@RestController
@RequestMapping("/api/inseminasi")
public class InseminasiController {
    @Autowired
    private InseminasiRepository inseminasiRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InseminasiService inseminasiService;

    private static final Logger logger = LoggerFactory.getLogger(InseminasiController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<InseminasiResponse> getInseminasi(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return inseminasiService.getAllInseminasi(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createInseminasi(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody InseminasiRequest inseminasiRequest) {
        Inseminasi inseminasi = inseminasiService.createInseminasi(currentUser, inseminasiRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{inseminasiId}")
                .buildAndExpand(inseminasi.getIdInseminasi()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Inseminasi Created Successfully"));
    }

    @PutMapping("/{inseminasiId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateInseminasiById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "inseminasiId") String inseminasiId, @Valid @RequestBody InseminasiRequest inseminasiRequest) {
        Inseminasi inseminasi = inseminasiService.updateInseminasi(inseminasiRequest, inseminasiId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{inseminasiId}")
                .buildAndExpand(inseminasi.getIdInseminasi()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Inseminasi Updated Successfully"));
    }

    @GetMapping("/{inseminasiId}")
    @Secured("ROLE_ADMINISTRATOR")
    public InseminasiResponse getInseminasiById(@PathVariable String inseminasiId) {
        return inseminasiService.getInseminasiById(inseminasiId);
    }

    @DeleteMapping("/{inseminasiId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteInseminasi(@PathVariable (value = "inseminasiId") String inseminasiId){
        inseminasiService.deleteInseminasiById(inseminasiId);
        return HttpStatus.FORBIDDEN;
    }
}
