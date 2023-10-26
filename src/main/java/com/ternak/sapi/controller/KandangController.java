package com.ternak.sapi.controller;

import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.kandang.KandangRequest;
import com.ternak.sapi.payload.kandang.KandangResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.KandangService;
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
import com.ternak.sapi.repository.KandangRepository;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/kandang")
public class KandangController {
    @Autowired
    private KandangRepository kandangRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KandangService kandangService;

    private static final Logger logger = LoggerFactory.getLogger(KandangController.class);

    @GetMapping
    @Secured({"ROLE_ADMINISTRATOR" , "ROLE_LECTURE"})
    public PagedResponse<KandangResponse> getKandang(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return kandangService.getAllKandang(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createKandang(@CurrentUser UserPrincipal currentUser, @Valid @ModelAttribute KandangRequest kandangRequest, 
            @RequestParam( "file") MultipartFile file) throws IOException{
        Kandang kandang = kandangService.createKandang(currentUser, kandangRequest, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{kandangId}")
                .buildAndExpand(kandang.getIdKandang()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Kandang Created Successfully"));
    }

    @PutMapping("/{kandangId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateKandangById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "kandangId") String kandangId, @Valid @RequestBody KandangRequest kandangRequest) {
        Kandang kandang = kandangService.updateKandang(kandangRequest, kandangId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{kandangId}")
                .buildAndExpand(kandang.getIdKandang()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Kandang Updated Successfully"));
    }

    @GetMapping("/{kandangId}")
    @Secured("ROLE_ADMINISTRATOR")
    public KandangResponse getKandangById(@PathVariable String kandangId) {
        return kandangService.getKandangById(kandangId);
    }

    @DeleteMapping("/{kandangId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteKandang(@PathVariable (value = "kandangId") String kandangId){
        kandangService.deleteKandangById(kandangId);
        return HttpStatus.FORBIDDEN;
    }
}
