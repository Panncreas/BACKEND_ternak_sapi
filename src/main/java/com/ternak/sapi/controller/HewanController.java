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
        @CurrentUser UserPrincipal currentUser,
        @RequestParam("kodeEartagNasional") String kodeEartagNasional,
        @RequestParam("provinsi") String provinsi,
        @RequestParam("kabupaten") String kabupaten,
        @RequestParam("kecamatan") String kecamatan,
        @RequestParam("desa") String desa,
        @RequestParam("namaPeternak") String namaPeternak,
        @RequestParam("idPeternak") String idPeternak,
        @RequestParam("nikPeternak") String nikPeternak,
        @RequestParam("spesies") String spesies,
        @RequestParam("sex") String sex,
        @RequestParam("umur") String umur,
        @RequestParam("identifikasiHewan") String identifikasiHewan,
        @RequestParam("petugasPendaftar") String petugasPendaftar,
        @RequestParam("tanggalTerdaftar") String tanggalTerdaftar,
        @RequestParam("image") MultipartFile image,
        HewanRequest hewanRequest) {
    
    try {
        Hewan hewan = hewanService.createHewan(currentUser, image, hewanRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{hewanId}")
                .buildAndExpand(hewan.getKodeEartagNasional()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Hewan Created Successfully"));
    } catch (IOException e) {
        // Handle the exception appropriately, e.g., return an error response.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Failed to create Hewan"));
    }
}


@PutMapping("/{hewanId}")
@Secured("ROLE_ADMINISTRATOR")
public ResponseEntity<?> updateHewanById(
        @CurrentUser UserPrincipal currentUser,
        @PathVariable(value = "hewanId") String hewanId,
        @RequestParam("kodeEartagNasional") String kodeEartagNasional,
        @RequestParam("provinsi") String provinsi,
        @RequestParam("kabupaten") String kabupaten,
        @RequestParam("kecamatan") String kecamatan,
        @RequestParam("desa") String desa,
        @RequestParam("namaPeternak") String namaPeternak,
        @RequestParam("idPeternak") String idPeternak,
        @RequestParam("nikPeternak") String nikPeternak,
        @RequestParam("spesies") String spesies,
        @RequestParam("sex") String sex,
        @RequestParam("umur") String umur,
        @RequestParam("identifikasiHewan") String identifikasiHewan,
        @RequestParam("petugasPendaftar") String petugasPendaftar,
        @RequestParam("tanggalTerdaftar") String tanggalTerdaftar,
        @RequestParam("image") MultipartFile image,
        HewanRequest hewanRequest) {

   

    Hewan hewan = hewanService.updateHewan(hewanRequest, hewanId, image, currentUser);

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
