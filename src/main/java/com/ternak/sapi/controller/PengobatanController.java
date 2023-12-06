package com.ternak.sapi.controller;

import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.pengobatan.PengobatanRequest;
import com.ternak.sapi.payload.pengobatan.PengobatanResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.PengobatanService;
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
import com.ternak.sapi.repository.PengobatanRepository;

@RestController
@RequestMapping("/api/pengobatan")
public class PengobatanController {
    @Autowired
    private PengobatanRepository pengobatanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PengobatanService pengobatanService;

    private static final Logger logger = LoggerFactory.getLogger(PengobatanController.class);

    @GetMapping
    @Secured({"ROLE_ADMINISTRATOR" , "ROLE_PETUGAS", "ROLE_PETERNAK"})
    public PagedResponse<PengobatanResponse> getPengobatan(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pengobatanService.getAllPengobatan(page, size);
    }

    @PostMapping
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_PETUGAS"})
    public ResponseEntity<?> createPengobatan(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PengobatanRequest pengobatanRequest) {
        Pengobatan pengobatan = pengobatanService.createPengobatan(currentUser, pengobatanRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pengobatanId}")
                .buildAndExpand(pengobatan.getIdKasus()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pengobatan Created Successfully"));
    }

    @PutMapping("/{pengobatanId}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_PETUGAS"})
    public ResponseEntity<?> updatePengobatanById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "pengobatanId") String pengobatanId, @Valid @RequestBody PengobatanRequest pengobatanRequest) {
        Pengobatan pengobatan = pengobatanService.updatePengobatan(pengobatanRequest, pengobatanId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pengobatanId}")
                .buildAndExpand(pengobatan.getIdKasus()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Pengobatan Updated Successfully"));
    }

    @GetMapping("/{pengobatanId}")
    @Secured({"ROLE_ADMINISTRATOR" , "ROLE_PETUGAS", "ROLE_PETERNAK"})
    public PengobatanResponse getPengobatanById(@PathVariable String pengobatanId) {
        return pengobatanService.getPengobatanById(pengobatanId);
    }

    @DeleteMapping("/{pengobatanId}")
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_PETUGAS"})
    public HttpStatus deletePengobatan(@PathVariable (value = "pengobatanId") String pengobatanId){
        pengobatanService.deletePengobatanById(pengobatanId);
        return HttpStatus.FORBIDDEN;
    }
}
