package com.ternak.sapi.controller;

import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.petugas.PetugasRequest;
import com.ternak.sapi.payload.petugas.PetugasResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.PetugasService;
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
import com.ternak.sapi.repository.PetugasRepository;

@RestController
@RequestMapping("/api/petugas")
public class PetugasController {
    @Autowired
    private PetugasRepository petugasRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetugasService petugasService;

    private static final Logger logger = LoggerFactory.getLogger(PetugasController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<PetugasResponse> getPetugas(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return petugasService.getAllPetugas(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createPetugas(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PetugasRequest petugasRequest) {
        Petugas petugas = petugasService.createPetugas(currentUser, petugasRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{petugasId}")
                .buildAndExpand(petugas.getNikPetugas()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Petugas Created Successfully"));
    }

    @PutMapping("/{petugasId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updatePetugasById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "petugasId") String petugasId, @Valid @RequestBody PetugasRequest petugasRequest) {
        Petugas petugas = petugasService.updatePetugas(petugasRequest, petugasId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{petugasId}")
                .buildAndExpand(petugas.getNikPetugas()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Petugas Updated Successfully"));
    }

    @GetMapping("/{petugasId}")
    @Secured("ROLE_ADMINISTRATOR")
    public PetugasResponse getPetugasById(@PathVariable String petugasId) {
        return petugasService.getPetugasById(petugasId);
    }

    @DeleteMapping("/{petugasId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deletePetugas(@PathVariable (value = "petugasId") String petugasId){
        petugasService.deletePetugasById(petugasId);
        return HttpStatus.FORBIDDEN;
    }
}
