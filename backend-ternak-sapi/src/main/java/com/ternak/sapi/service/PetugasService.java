package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.petugas.PetugasRequest;
import com.ternak.sapi.payload.petugas.PetugasResponse;
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
import com.ternak.sapi.repository.PetugasRepository;

@Service
public class PetugasService {
    @Autowired
    private PetugasRepository petugasRepository;

    private static final Logger logger = LoggerFactory.getLogger(PetugasService.class);

    public PagedResponse<PetugasResponse> getAllPetugas(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Petugas> petugas = petugasRepository.findAll(pageable);

        if(petugas.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), petugas.getNumber(),
                    petugas.getSize(), petugas.getTotalElements(), petugas.getTotalPages(), petugas.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<PetugasResponse> petugasResponses = petugas.map(asResponse -> {
            PetugasResponse petugasResponse = new PetugasResponse();
            petugasResponse.setNikPetugas(asResponse.getNikPetugas());
            petugasResponse.setNamaPetugas(asResponse.getNamaPetugas());
            petugasResponse.setNoTelp(asResponse.getNoTelp());
            petugasResponse.setEmail(asResponse.getEmail());
            petugasResponse.setCreatedAt(asResponse.getCreatedAt());
            petugasResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return petugasResponse;
        }).getContent();

        return new PagedResponse<>(petugasResponses, petugas.getNumber(),
                petugas.getSize(), petugas.getTotalElements(), petugas.getTotalPages(), petugas.isLast(), 200);
    }

    public Petugas createPetugas(UserPrincipal currentUser, PetugasRequest petugasRequest) {
        Petugas petugas = new Petugas();
        petugas.setNikPetugas(petugasRequest.getNikPetugas());
        petugas.setNamaPetugas(petugasRequest.getNamaPetugas());
        petugas.setNoTelp(petugasRequest.getNoTelp());
        petugas.setEmail(petugasRequest.getEmail());
        petugas.setCreatedBy(currentUser.getId());
        petugas.setUpdatedBy(currentUser.getId());
        return petugasRepository.save(petugas);
    }

    public PetugasResponse getPetugasById(String petugasId) {
        Petugas petugas = petugasRepository.findById(petugasId).orElseThrow(
                () -> new ResourceNotFoundException("Petugas", "id", petugasId));

        PetugasResponse petugasResponse = new PetugasResponse();
        petugasResponse.setNikPetugas(petugas.getNikPetugas());
        petugasResponse.setNamaPetugas(petugas.getNamaPetugas());
        petugasResponse.setNoTelp(petugas.getNoTelp());
        petugasResponse.setEmail(petugas.getEmail());
        petugasResponse.setCreatedAt(petugas.getCreatedAt());
        petugasResponse.setUpdatedAt(petugas.getUpdatedAt());
        return petugasResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Petugas updatePetugas(PetugasRequest petugasReq, String id, UserPrincipal currentUser){
        return petugasRepository.findById(id).map(petugas -> {
            petugas.setNikPetugas(petugasReq.getNikPetugas());
            petugas.setNamaPetugas(petugasReq.getNamaPetugas());
            petugas.setNoTelp(petugasReq.getNoTelp());
            petugas.setEmail(petugasReq.getEmail());
            petugas.setCreatedBy(currentUser.getId());
            petugas.setUpdatedBy(currentUser.getId());
            return petugasRepository.save(petugas);
        }).orElseThrow(() -> new ResourceNotFoundException("Petugas" , "id" , id));
    }

    public void deletePetugasById(String id){
        Optional<Petugas> petugas = petugasRepository.findById(id);
        if(petugas.isPresent()){
            petugasRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Petugas", "id", id);
        }
    }
}