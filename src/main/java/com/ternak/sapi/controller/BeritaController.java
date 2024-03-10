package com.ternak.sapi.controller;

import com.ternak.sapi.model.Berita;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.berita.BeritaRequest;
import com.ternak.sapi.payload.berita.BeritaResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.BeritaService;
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
import com.ternak.sapi.repository.BeritaRepository;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/berita")
public class BeritaController {
    @Autowired
    private BeritaRepository beritaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeritaService beritaService;

    private static final Logger logger = LoggerFactory.getLogger(BeritaController.class);

    @GetMapping
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_PETUGAS", "ROLE_PETERNAK"})
    public PagedResponse<BeritaResponse> getBerita(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return beritaService.getAllBerita(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createBerita(@CurrentUser UserPrincipal currentUser, @Valid @ModelAttribute BeritaRequest beritaRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException{
        Berita berita = beritaService.createBerita(currentUser, beritaRequest, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{beritaId}")
                .buildAndExpand(berita.getIdBerita()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Berita Created Successfully"));
    }

    @PutMapping("/{beritaId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateBeritaById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "beritaId") Long beritaId, @Valid @ModelAttribute BeritaRequest beritaRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException{
        Berita berita = beritaService.updateBerita(beritaRequest, beritaId, currentUser, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{beritaId}")
                .buildAndExpand(berita.getIdBerita()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Berita Updated Successfully"));
    }

    @GetMapping("/{beritaId}")
    @Secured("ROLE_ADMINISTRATOR")
    public BeritaResponse getBeritaById(@PathVariable Long beritaId) {
        return beritaService.getBeritaById(beritaId);
    }

    @DeleteMapping("/{beritaId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteBerita(@PathVariable (value = "beritaId") Long beritaId){
        beritaService.deleteBeritaById(beritaId);
        return HttpStatus.FORBIDDEN;
    }
}
