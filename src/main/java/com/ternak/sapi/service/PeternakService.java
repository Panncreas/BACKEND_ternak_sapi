package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.peternak.PeternakRequest;
import com.ternak.sapi.payload.peternak.PeternakResponse;
import com.ternak.sapi.repository.PeternakRepository;
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


@Service
public class PeternakService {
    @Autowired
    private PeternakRepository peternakRepository;

    private static final Logger logger = LoggerFactory.getLogger(PeternakService.class);

    public PagedResponse<PeternakResponse> getAllPeternak(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Peternak> peternaks = peternakRepository.findAll(pageable);

        if(peternaks.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), peternaks.getNumber(),
                    peternaks.getSize(), peternaks.getTotalElements(), peternaks.getTotalPages(), peternaks.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<PeternakResponse> peternakResponses = peternaks.map(asResponse -> {
            PeternakResponse peternakResponse = new PeternakResponse();
            peternakResponse.setIdPeternak(asResponse.getIdPeternak());
            peternakResponse.setNikPeternak(asResponse.getNikPeternak());
            peternakResponse.setNamaPeternak(asResponse.getNamaPeternak());
            peternakResponse.setIdISIKHNAS(asResponse.getIdISIKHNAS());
            peternakResponse.setLokasi(asResponse.getLokasi());
             peternakResponse.setPetugasPendaftar(asResponse.getPetugasPendaftar());
            peternakResponse.setTanggalPendaftaran(asResponse.getTanggalPendaftaran());
            peternakResponse.setCreatedAt(asResponse.getCreatedAt());
            peternakResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return peternakResponse;
        }).getContent();

        return new PagedResponse<>(peternakResponses, peternaks.getNumber(),
                peternaks.getSize(), peternaks.getTotalElements(), peternaks.getTotalPages(), peternaks.isLast(), 200);
    }

    public Peternak createPeternak(UserPrincipal currentUser, PeternakRequest peternakRequest) {
        Peternak peternak = new Peternak();
        
        //Menambahkan atau memanggil function ID agar bisa input manual value ID
        peternak.setIdPeternak(peternakRequest.getIdPeternak());
        peternak.setNikPeternak(peternakRequest.getNikPeternak());
        peternak.setNamaPeternak(peternakRequest.getNamaPeternak());
        peternak.setIdISIKHNAS(peternakRequest.getIdISIKHNAS());
        peternak.setLokasi(peternakRequest.getLokasi());
        peternak.setPetugasPendaftar(peternakRequest.getPetugasPendaftar());
        peternak.setTanggalPendaftaran(peternakRequest.getTanggalPendaftaran());
        peternak.setCreatedBy(currentUser.getId());
        peternak.setUpdatedBy(currentUser.getId());
        return peternakRepository.save(peternak);
    }

    public PeternakResponse getPeternakById(String peternakId) {
        Peternak peternak = peternakRepository.findById(peternakId).orElseThrow(
                () -> new ResourceNotFoundException("Peternak", "id", peternakId));

        PeternakResponse peternakResponse = new PeternakResponse();
        peternakResponse.setIdPeternak(peternak.getIdPeternak());
        peternakResponse.setNikPeternak(peternak.getNikPeternak());
        peternakResponse.setNamaPeternak(peternak.getNamaPeternak());
        peternakResponse.setIdISIKHNAS(peternak.getIdISIKHNAS());
        peternakResponse.setLokasi(peternak.getLokasi());
        peternakResponse.setPetugasPendaftar(peternak.getPetugasPendaftar());
        peternakResponse.setTanggalPendaftaran(peternak.getTanggalPendaftaran());
        peternakResponse.setCreatedAt(peternak.getCreatedAt());
        peternakResponse.setUpdatedAt(peternak.getUpdatedAt());
        return peternakResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Peternak updatePeternak(PeternakRequest peternakReq, String id, UserPrincipal currentUser){
        return peternakRepository.findById(id).map(peternak -> {
            peternak.setIdPeternak(peternakReq.getIdPeternak());
            peternak.setNikPeternak(peternakReq.getNikPeternak());
            peternak.setNamaPeternak(peternakReq.getNamaPeternak());
            peternak.setIdISIKHNAS(peternakReq.getIdISIKHNAS());
            peternak.setLokasi(peternakReq.getLokasi());
            peternak.setPetugasPendaftar(peternakReq.getPetugasPendaftar());
            peternak.setTanggalPendaftaran(peternakReq.getTanggalPendaftaran());
            peternak.setCreatedBy(currentUser.getId());
            peternak.setUpdatedBy(currentUser.getId());
            return peternakRepository.save(peternak);
        }).orElseThrow(() -> new ResourceNotFoundException("Peternak" , "id" , id));
    }

    public void deletePeternakById(String id){
        Optional<Peternak> peternak = peternakRepository.findById(id);
        if(peternak.isPresent()){
            peternakRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Peternak", "id", id);
        }
    }
}