package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Berita;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.berita.BeritaRequest;
import com.ternak.sapi.payload.berita.BeritaResponse;
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
import com.ternak.sapi.repository.BeritaRepository;

@Service
public class BeritaService {
    @Autowired
    private BeritaRepository beritaRepository;

    private static final Logger logger = LoggerFactory.getLogger(BeritaService.class);

    public PagedResponse<BeritaResponse> getAllBerita(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Berita> berita = beritaRepository.findAll(pageable);

        if(berita.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), berita.getNumber(),
                    berita.getSize(), berita.getTotalElements(), berita.getTotalPages(), berita.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<BeritaResponse> beritaResponses = berita.map(asResponse -> {
            BeritaResponse beritaResponse = new BeritaResponse();
            beritaResponse.setIdBerita(asResponse.getIdBerita());
            beritaResponse.setJudul(asResponse.getJudul());
            beritaResponse.setTglPembuatan(asResponse.getTglPembuatan());
            beritaResponse.setPembuat(asResponse.getPembuat());
            beritaResponse.setIsiBerita(asResponse.getIsiBerita());
            beritaResponse.setCreatedAt(asResponse.getCreatedAt());
            beritaResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return beritaResponse;
        }).getContent();

        return new PagedResponse<>(beritaResponses, berita.getNumber(),
                berita.getSize(), berita.getTotalElements(), berita.getTotalPages(), berita.isLast(), 200);
    }

    public Berita createBerita(UserPrincipal currentUser, BeritaRequest beritaRequest) {
        Berita berita = new Berita();
        berita.setJudul(beritaRequest.getJudul());
        berita.setTglPembuatan(beritaRequest.getTglPembuatan());
        berita.setPembuat(beritaRequest.getPembuat());
        berita.setIsiBerita(beritaRequest.getIsiBerita());
        berita.setCreatedBy(currentUser.getId());
        berita.setUpdatedBy(currentUser.getId());
        return beritaRepository.save(berita);
    }

    public BeritaResponse getBeritaById(Long beritaId) {
        Berita berita = beritaRepository.findById(beritaId).orElseThrow(
                () -> new ResourceNotFoundException("Berita", "id", beritaId));

        BeritaResponse beritaResponse = new BeritaResponse();
        beritaResponse.setIdBerita(berita.getIdBerita());
        beritaResponse.setJudul(berita.getJudul());
        beritaResponse.setTglPembuatan(berita.getTglPembuatan());
        beritaResponse.setPembuat(berita.getPembuat());
        beritaResponse.setIsiBerita(berita.getIsiBerita());
        beritaResponse.setCreatedAt(berita.getCreatedAt());
        beritaResponse.setUpdatedAt(berita.getUpdatedAt());
        return beritaResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Berita updateBerita(BeritaRequest beritaReq, Long id, UserPrincipal currentUser){
        return beritaRepository.findById(id).map(berita -> {
            berita.setJudul(beritaReq.getJudul());
            berita.setTglPembuatan(beritaReq.getTglPembuatan());
            berita.setPembuat(beritaReq.getPembuat());
            berita.setIsiBerita(beritaReq.getIsiBerita());
            berita.setCreatedBy(currentUser.getId());
            berita.setUpdatedBy(currentUser.getId());
            return beritaRepository.save(berita);
        }).orElseThrow(() -> new ResourceNotFoundException("Berita" , "id" , id));
    }

    public void deleteBeritaById(Long id){
        Optional<Berita> berita = beritaRepository.findById(id);
        if(berita.isPresent()){
            beritaRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Berita", "id", id);
        }
    }
}