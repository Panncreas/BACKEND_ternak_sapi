package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Pkb;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.pkb.PkbRequest;
import com.ternak.sapi.payload.pkb.PkbResponse;
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
import com.ternak.sapi.repository.PkbRepository;

@Service
public class PkbService {
    @Autowired
    private PkbRepository pkbRepository;

    private static final Logger logger = LoggerFactory.getLogger(PkbService.class);

    public PagedResponse<PkbResponse> getAllPkb(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Pkb> pkb = pkbRepository.findAll(pageable);

        if(pkb.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), pkb.getNumber(),
                    pkb.getSize(), pkb.getTotalElements(), pkb.getTotalPages(), pkb.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<PkbResponse> pkbResponses = pkb.map(asResponse -> {
            PkbResponse pkbResponse = new PkbResponse();
            pkbResponse.setIdKejadian(asResponse.getIdKejadian());
            pkbResponse.setTanggalPkb(asResponse.getTanggalPkb());
            pkbResponse.setLokasi(asResponse.getLokasi());
            pkbResponse.setIdPeternak(asResponse.getIdPeternak());
            pkbResponse.setIdHewan(asResponse.getIdHewan());
            pkbResponse.setSpesies(asResponse.getSpesies());
            pkbResponse.setKategori(asResponse.getKategori());
            pkbResponse.setJumlah(asResponse.getJumlah());
            pkbResponse.setUmurKebuntingan(asResponse.getUmurKebuntingan());
            pkbResponse.setPemeriksaKebuntingan(asResponse.getPemeriksaKebuntingan());
            pkbResponse.setCreatedAt(asResponse.getCreatedAt());
            pkbResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return pkbResponse;
        }).getContent();

        return new PagedResponse<>(pkbResponses, pkb.getNumber(),
                pkb.getSize(), pkb.getTotalElements(), pkb.getTotalPages(), pkb.isLast(), 200);
    }

    public Pkb createPkb(UserPrincipal currentUser, PkbRequest pkbRequest) {
        Pkb pkb = new Pkb();
        pkb.setIdKejadian(pkbRequest.getIdKejadian());
        pkb.setTanggalPkb(pkbRequest.getTanggalPkb());
        pkb.setLokasi(pkbRequest.getLokasi());
        pkb.setIdPeternak(pkbRequest.getIdPeternak());
        pkb.setIdHewan(pkbRequest.getIdHewan());
        pkb.setSpesies(pkbRequest.getSpesies());
        pkb.setKategori(pkbRequest.getKategori());
        pkb.setJumlah(pkbRequest.getJumlah());
        pkb.setUmurKebuntingan(pkbRequest.getUmurKebuntingan());
        pkb.setPemeriksaKebuntingan(pkbRequest.getPemeriksaKebuntingan());
        pkb.setCreatedBy(currentUser.getId());
        pkb.setUpdatedBy(currentUser.getId());
        return pkbRepository.save(pkb);
    }

    public PkbResponse getPkbById(String pkbId) {
        Pkb pkb = pkbRepository.findById(pkbId).orElseThrow(
                () -> new ResourceNotFoundException("Pkb", "id", pkbId));

        PkbResponse pkbResponse = new PkbResponse();
        pkbResponse.setIdKejadian(pkb.getIdKejadian());
        pkbResponse.setTanggalPkb(pkb.getTanggalPkb());
        pkbResponse.setLokasi(pkb.getLokasi());
        pkbResponse.setIdPeternak(pkb.getIdPeternak());
        pkbResponse.setIdHewan(pkb.getIdHewan());
        pkbResponse.setSpesies(pkb.getSpesies());
        pkbResponse.setKategori(pkb.getKategori());
        pkbResponse.setJumlah(pkb.getJumlah());
        pkbResponse.setUmurKebuntingan(pkb.getUmurKebuntingan());
        pkbResponse.setPemeriksaKebuntingan(pkb.getPemeriksaKebuntingan());
        pkbResponse.setCreatedAt(pkb.getCreatedAt());
        pkbResponse.setUpdatedAt(pkb.getUpdatedAt());
        return pkbResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Pkb updatePkb(PkbRequest pkbReq, String idKejadian, UserPrincipal currentUser){
        return pkbRepository.findById(idKejadian).map(pkb -> {
            pkb.setIdKejadian(pkbReq.getIdKejadian());
            pkb.setTanggalPkb(pkbReq.getTanggalPkb());
            pkb.setLokasi(pkbReq.getLokasi());
            pkb.setIdPeternak(pkbReq.getIdPeternak());
            pkb.setIdHewan(pkbReq.getIdHewan());
            pkb.setSpesies(pkbReq.getSpesies());
            pkb.setKategori(pkbReq.getKategori());
            pkb.setJumlah(pkbReq.getJumlah());
            pkb.setUmurKebuntingan(pkbReq.getUmurKebuntingan());
            pkb.setPemeriksaKebuntingan(pkbReq.getPemeriksaKebuntingan());
            
            
            pkb.setCreatedBy(currentUser.getId());
            pkb.setUpdatedBy(currentUser.getId());
            return pkbRepository.save(pkb);
        }).orElseThrow(() -> new ResourceNotFoundException("Pkb" , "idKejadian" , idKejadian));
    }

    public void deletePkbById(String idKejadian){
        Optional<Pkb> pkb = pkbRepository.findById(idKejadian);
        if(pkb.isPresent()){
            pkbRepository.deleteById(idKejadian);
        }else{
            throw new ResourceNotFoundException("Pkb", "idKejadian", idKejadian);
        }
    }
}