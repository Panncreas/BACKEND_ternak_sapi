package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Pengobatan;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.pengobatan.PengobatanRequest;
import com.ternak.sapi.payload.pengobatan.PengobatanResponse;
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
import com.ternak.sapi.repository.PengobatanRepository;

@Service
public class PengobatanService {
    @Autowired
    private PengobatanRepository pengobatanRepository;

    private static final Logger logger = LoggerFactory.getLogger(PengobatanService.class);

    public PagedResponse<PengobatanResponse> getAllPengobatan(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Pengobatan> pengobatan = pengobatanRepository.findAll(pageable);

        if(pengobatan.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), pengobatan.getNumber(),
                    pengobatan.getSize(), pengobatan.getTotalElements(), pengobatan.getTotalPages(), pengobatan.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<PengobatanResponse> pengobatanResponses = pengobatan.map(asResponse -> {
            PengobatanResponse pengobatanResponse = new PengobatanResponse();
            pengobatanResponse.setIdKasus(asResponse.getIdKasus());
            pengobatanResponse.setTanggalPengobatan(asResponse.getTanggalPengobatan());
            pengobatanResponse.setTanggalKasus(asResponse.getTanggalKasus());
            pengobatanResponse.setNamaPetugas(asResponse.getNamaPetugas());
            pengobatanResponse.setNamaInfrastruktur(asResponse.getNamaInfrastruktur());
            pengobatanResponse.setLokasi(asResponse.getLokasi());
            pengobatanResponse.setDosis(asResponse.getDosis());
            pengobatanResponse.setSindrom(asResponse.getSindrom());
            pengobatanResponse.setDiagnosaBanding(asResponse.getDiagnosaBanding());
            pengobatanResponse.setCreatedAt(asResponse.getCreatedAt());
            pengobatanResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return pengobatanResponse;
        }).getContent();

        return new PagedResponse<>(pengobatanResponses, pengobatan.getNumber(),
                pengobatan.getSize(), pengobatan.getTotalElements(), pengobatan.getTotalPages(), pengobatan.isLast(), 200);
    }

    public Pengobatan createPengobatan(UserPrincipal currentUser, PengobatanRequest pengobatanRequest) {
        Pengobatan pengobatan = new Pengobatan();
        pengobatan.setIdKasus(pengobatanRequest.getIdKasus());
        pengobatan.setTanggalPengobatan(pengobatanRequest.getTanggalPengobatan());
        pengobatan.setTanggalKasus(pengobatanRequest.getTanggalKasus());
        pengobatan.setNamaPetugas(pengobatanRequest.getNamaPetugas());
        pengobatan.setNamaInfrastruktur(pengobatanRequest.getNamaInfrastruktur());
        pengobatan.setLokasi(pengobatanRequest.getLokasi());
        pengobatan.setDosis(pengobatanRequest.getDosis());
        pengobatan.setSindrom(pengobatanRequest.getSindrom());
        pengobatan.setDiagnosaBanding(pengobatanRequest.getDiagnosaBanding());
        pengobatan.setCreatedBy(currentUser.getId());
        pengobatan.setUpdatedBy(currentUser.getId());
        return pengobatanRepository.save(pengobatan);
    }

    public PengobatanResponse getPengobatanById(String pengobatanId) {
        Pengobatan pengobatan = pengobatanRepository.findById(pengobatanId).orElseThrow(
                () -> new ResourceNotFoundException("Pengobatan", "id", pengobatanId));

        PengobatanResponse pengobatanResponse = new PengobatanResponse();
        pengobatanResponse.setIdKasus(pengobatan.getIdKasus());
        pengobatanResponse.setTanggalPengobatan(pengobatan.getTanggalPengobatan());
        pengobatanResponse.setTanggalKasus(pengobatan.getTanggalKasus());
        pengobatanResponse.setNamaPetugas(pengobatan.getNamaPetugas());
        pengobatanResponse.setNamaInfrastruktur(pengobatan.getNamaInfrastruktur());
        pengobatanResponse.setLokasi(pengobatan.getLokasi());
        pengobatanResponse.setDosis(pengobatan.getDosis());
        pengobatanResponse.setSindrom(pengobatan.getSindrom());
        pengobatanResponse.setDiagnosaBanding(pengobatan.getDiagnosaBanding());
        pengobatanResponse.setCreatedAt(pengobatan.getCreatedAt());
        pengobatanResponse.setUpdatedAt(pengobatan.getUpdatedAt());
        return pengobatanResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Pengobatan updatePengobatan(PengobatanRequest pengobatanReq, String id, UserPrincipal currentUser){
        return pengobatanRepository.findById(id).map(pengobatan -> {
            pengobatan.setIdKasus(pengobatanReq.getIdKasus());
            pengobatan.setTanggalPengobatan(pengobatanReq.getTanggalPengobatan());
            pengobatan.setTanggalKasus(pengobatanReq.getTanggalKasus());
            pengobatan.setNamaPetugas(pengobatanReq.getNamaPetugas());
            pengobatan.setNamaInfrastruktur(pengobatanReq.getNamaInfrastruktur());
            pengobatan.setLokasi(pengobatanReq.getLokasi());
            pengobatan.setDosis(pengobatanReq.getDosis());
            pengobatan.setSindrom(pengobatanReq.getSindrom());
            pengobatan.setDiagnosaBanding(pengobatanReq.getDiagnosaBanding());
            pengobatan.setCreatedBy(currentUser.getId());
            pengobatan.setUpdatedBy(currentUser.getId());
            return pengobatanRepository.save(pengobatan);
        }).orElseThrow(() -> new ResourceNotFoundException("Pengobatan" , "id" , id));
    }

    public void deletePengobatanById(String id){
        Optional<Pengobatan> pengobatan = pengobatanRepository.findById(id);
        if(pengobatan.isPresent()){
            pengobatanRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Pengobatan", "id", id);
        }
    }
}