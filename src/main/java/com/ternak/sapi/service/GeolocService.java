package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Geoloc;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.geoloc.GeolocRequest;
import com.ternak.sapi.payload.geoloc.GeolocResponse;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.ternak.sapi.repository.GeolocRepository;

@Service
public class GeolocService {
    @Autowired
    private GeolocRepository geolocRepository;

    private static final Logger logger = LoggerFactory.getLogger(GeolocService.class);

    public PagedResponse<GeolocResponse> getAllGeoloc(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Geoloc> geoloc = geolocRepository.findAll(pageable);

        if(geoloc.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), geoloc.getNumber(),
                    geoloc.getSize(), geoloc.getTotalElements(), geoloc.getTotalPages(), geoloc.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<GeolocResponse> geolocResponses = geoloc.map(asResponse -> {
            GeolocResponse geolocResponse = new GeolocResponse();
            geolocResponse.setId(asResponse.getId());
            geolocResponse.setLatitude(asResponse.getLatitude());
            geolocResponse.setLongitude(asResponse.getLongitude());
            geolocResponse.setCreatedAt(asResponse.getCreatedAt());
            geolocResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return geolocResponse;
        }).getContent();

        return new PagedResponse<>(geolocResponses, geoloc.getNumber(),
                geoloc.getSize(), geoloc.getTotalElements(), geoloc.getTotalPages(), geoloc.isLast(), 200);
    }

    public Geoloc createGeoloc(UserPrincipal currentUser, GeolocRequest geolocRequest) {
        Geoloc geoloc = new Geoloc();
        geoloc.setLatitude(geolocRequest.getLatitude());
        geoloc.setLongitude(geolocRequest.getLongitude());
        geoloc.setCreatedBy(currentUser.getId());
        geoloc.setUpdatedBy(currentUser.getId());
        return geolocRepository.save(geoloc);
    }

    public GeolocResponse getGeolocById(String geolocId) {
        Geoloc geoloc = geolocRepository.findById(geolocId).orElseThrow(
                () -> new ResourceNotFoundException("Geoloc", "id", geolocId));

        GeolocResponse geolocResponse = new GeolocResponse();
        geolocResponse.setId(geoloc.getId());
        geolocResponse.setLatitude(geoloc.getLatitude());
        geolocResponse.setLongitude(geoloc.getLongitude());
        geolocResponse.setCreatedAt(geoloc.getCreatedAt());
        geolocResponse.setUpdatedAt(geoloc.getUpdatedAt());
        return geolocResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Geoloc updateGeoloc(GeolocRequest geolocReq, String id, UserPrincipal currentUser){
        return geolocRepository.findById(id).map(geoloc -> {
            geoloc.setLatitude(geolocReq.getLatitude());
            geoloc.setLongitude(geolocReq.getLongitude());
            geoloc.setCreatedBy(currentUser.getId());
            geoloc.setUpdatedBy(currentUser.getId());
            return geolocRepository.save(geoloc);
        }).orElseThrow(() -> new ResourceNotFoundException("Geoloc" , "id" , id));
    }

    public void deleteGeolocById(String id){
        Optional<Geoloc> geoloc = geolocRepository.findById(id);
        if(geoloc.isPresent()){
            geolocRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Geoloc", "id", id);
        }
    }
}