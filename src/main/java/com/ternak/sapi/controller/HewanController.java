package com.ternak.sapi.controller;

import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.hewan.HewanRequest;
import com.ternak.sapi.payload.hewan.HewanResponse;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.HewanService;
import com.ternak.sapi.util.AppConstants;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/hewan")
public class HewanController {
    @Autowired
    private HewanRepository hewanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HewanService hewanService;

    private static final Logger logger = LoggerFactory.getLogger(HewanController.class);

    @GetMapping
    @Secured({"ROLE_ADMINISTRATOR" , "ROLE_LECTURE"})
    public PagedResponse<HewanResponse> getHewan(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return hewanService.getAllHewan(page, size);
    }

    //diganti request
    @PostMapping
@Secured("ROLE_ADMINISTRATOR")
public ResponseEntity<?> createHewan(
        @CurrentUser UserPrincipal currentUser,@Valid @ModelAttribute HewanRequest hewanRequest, @RequestParam( "image") MultipartFile file
        )throws IOException {
        Hewan hewan = hewanService.createHewan(currentUser, hewanRequest, file);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{departmentId}")
                .buildAndExpand(hewan.getKodeEartagNasional()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Department Created Successfully"));
}


@PutMapping("/{hewanId}")
@Secured("ROLE_ADMINISTRATOR")
 public ResponseEntity<?> updateKandangById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "hewanId") String hewanId, @Valid @RequestBody HewanRequest hewanRequest) {
        Hewan hewan = hewanService.updateHewan(hewanRequest, hewanId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{hewanId}")
                .buildAndExpand(hewan.getKodeEartagNasional()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Hewan Updated Successfully"));
    }

    @GetMapping("/{hewanId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HewanResponse getHewanById(@PathVariable String hewanId) {
        return hewanService.getHewanById(hewanId);
    }

    @DeleteMapping("/{hewanId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteHewan(@PathVariable (value = "hewanId") String hewanId){
        hewanService.deleteHewanById(hewanId);
        return HttpStatus.FORBIDDEN;
    }
}
