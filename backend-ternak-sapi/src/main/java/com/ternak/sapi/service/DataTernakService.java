package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.DataTernak;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.dataTernak.DataTernakRequest;
import com.ternak.sapi.payload.dataTernak.DataTernakResponse;
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
import com.ternak.sapi.repository.DataTernakRepository;

@Service
public class DataTernakService {
    @Autowired
    private DataTernakRepository dataTernakRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataTernakService.class);

    public PagedResponse<DataTernakResponse> getAllDataTernak(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<DataTernak> dataTernak = dataTernakRepository.findAll(pageable);

        if(dataTernak.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), dataTernak.getNumber(),
                    dataTernak.getSize(), dataTernak.getTotalElements(), dataTernak.getTotalPages(), dataTernak.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<DataTernakResponse> dataTernakResponses = dataTernak.map(asResponse -> {
            DataTernakResponse dataTernakResponse = new DataTernakResponse();
            dataTernakResponse.setId(asResponse.getId());
            dataTernakResponse.setProvinsi(asResponse.getProvinsi());
            dataTernakResponse.setKabupaten(asResponse.getKabupaten());
            dataTernakResponse.setKecamatan(asResponse.getKecamatan());
            dataTernakResponse.setDesa(asResponse.getDesa());
            dataTernakResponse.setSpesies(asResponse.getSpesies());
            dataTernakResponse.setPetugasPendaftar(asResponse.getPetugasPendaftar());
            dataTernakResponse.setPemilik(asResponse.getPemilik());
            dataTernakResponse.setCreatedAt(asResponse.getCreatedAt());
            dataTernakResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return dataTernakResponse;
        }).getContent();

        return new PagedResponse<>(dataTernakResponses, dataTernak.getNumber(),
                dataTernak.getSize(), dataTernak.getTotalElements(), dataTernak.getTotalPages(), dataTernak.isLast(), 200);
    }

    public DataTernak createDataTernak(UserPrincipal currentUser, DataTernakRequest dataTernakRequest) {
        DataTernak dataTernak = new DataTernak();
        dataTernak.setId(dataTernakRequest.getId());
        dataTernak.setProvinsi(dataTernakRequest.getProvinsi());
        dataTernak.setKabupaten(dataTernakRequest.getKabupaten());
        dataTernak.setKecamatan(dataTernakRequest.getKecamatan());
        dataTernak.setDesa(dataTernakRequest.getDesa());
        dataTernak.setSpesies(dataTernakRequest.getSpesies());
        dataTernak.setPetugasPendaftar(dataTernakRequest.getPetugasPendaftar());
        dataTernak.setPemilik(dataTernakRequest.getPemilik());
        dataTernak.setCreatedBy(currentUser.getId());
        dataTernak.setUpdatedBy(currentUser.getId());
        return dataTernakRepository.save(dataTernak);
    }

    public DataTernakResponse getDataTernakById(String dataTernakId) {
        DataTernak dataTernak = dataTernakRepository.findById(dataTernakId).orElseThrow(
                () -> new ResourceNotFoundException("DataTernak", "id", dataTernakId));

        DataTernakResponse dataTernakResponse = new DataTernakResponse();
        dataTernakResponse.setId(dataTernak.getId());
        dataTernakResponse.setProvinsi(dataTernak.getProvinsi());
        dataTernakResponse.setKabupaten(dataTernak.getKabupaten());
        dataTernakResponse.setKecamatan(dataTernak.getKecamatan());
        dataTernakResponse.setDesa(dataTernak.getDesa());
        dataTernakResponse.setSpesies(dataTernak.getSpesies());
        dataTernakResponse.setPetugasPendaftar(dataTernak.getPetugasPendaftar());
        dataTernakResponse.setPemilik(dataTernak.getPemilik());
        dataTernakResponse.setCreatedAt(dataTernak.getCreatedAt());
        dataTernakResponse.setUpdatedAt(dataTernak.getUpdatedAt());
        return dataTernakResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public DataTernak updateDataTernak(DataTernakRequest dataTernakReq, String id, UserPrincipal currentUser){
        return dataTernakRepository.findById(id).map(dataTernak -> {
            dataTernak.setId(dataTernakReq.getId());
            dataTernak.setProvinsi(dataTernakReq.getProvinsi());
            dataTernak.setKabupaten(dataTernakReq.getKabupaten());
            dataTernak.setKecamatan(dataTernakReq.getKecamatan());
            dataTernak.setDesa(dataTernakReq.getDesa());
            dataTernak.setSpesies(dataTernakReq.getSpesies());
            dataTernak.setPetugasPendaftar(dataTernakReq.getPetugasPendaftar());
            dataTernak.setPemilik(dataTernakReq.getPemilik());
            dataTernak.setCreatedBy(currentUser.getId());
            dataTernak.setUpdatedBy(currentUser.getId());
            return dataTernakRepository.save(dataTernak);
        }).orElseThrow(() -> new ResourceNotFoundException("DataTernak" , "id" , id));
    }

    public void deleteDataTernakById(String id){
        Optional<DataTernak> dataTernak = dataTernakRepository.findById(id);
        if(dataTernak.isPresent()){
            dataTernakRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("DataTernak", "id", id);
        }
    }
}