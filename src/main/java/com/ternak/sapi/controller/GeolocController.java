package com.ternak.sapi.controller;

import com.ternak.sapi.model.Geoloc;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.geoloc.GeolocRequest;
import com.ternak.sapi.payload.geoloc.GeolocResponse;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.GeolocService;
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
import com.ternak.sapi.repository.GeolocRepository;

@RestController
@RequestMapping("/api/geoloc")
public class GeolocController {
    @Autowired
    private GeolocRepository geolocRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeolocService geolocService;

    private static final Logger logger = LoggerFactory.getLogger(GeolocController.class);

    @GetMapping
    @Secured({"ROLE_ADMINISTRATOR" , "ROLE_LECTURE"})
    public PagedResponse<GeolocResponse> getGeoloc(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return geolocService.getAllGeoloc(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createGeoloc(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody GeolocRequest geolocRequest) {
        Geoloc geoloc = geolocService.createGeoloc(currentUser, geolocRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{geolocId}")
                .buildAndExpand(geoloc.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Geoloc Created Successfully"));
    }

    @PutMapping("/{geolocId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateGeolocById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "geolocId") String geolocId, @Valid @RequestBody GeolocRequest geolocRequest) {
        Geoloc geoloc = geolocService.updateGeoloc(geolocRequest, geolocId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{geolocId}")
                .buildAndExpand(geoloc.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Geoloc Updated Successfully"));
    }

    @GetMapping("/{geolocId}")
    @Secured("ROLE_ADMINISTRATOR")
    public GeolocResponse getGeolocById(@PathVariable String geolocId) {
        return geolocService.getGeolocById(geolocId);
    }

    @DeleteMapping("/{geolocId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteGeoloc(@PathVariable (value = "geolocId") String geolocId){
        geolocService.deleteGeolocById(geolocId);
        return HttpStatus.FORBIDDEN;
    }
}
