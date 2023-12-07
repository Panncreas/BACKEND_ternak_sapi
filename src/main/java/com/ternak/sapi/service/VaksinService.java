package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Vaksin;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.vaksin.VaksinRequest;
import com.ternak.sapi.payload.vaksin.VaksinResponse;
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
import com.ternak.sapi.repository.VaksinRepository;

@Service
public class VaksinService {
    @Autowired
    private VaksinRepository vaksinRepository;

    private static final Logger logger = LoggerFactory.getLogger(VaksinService.class);

    public PagedResponse<VaksinResponse> getAllVaksin(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Vaksin> vaksin = vaksinRepository.findAll(pageable);

        if(vaksin.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), vaksin.getNumber(),
                    vaksin.getSize(), vaksin.getTotalElements(), vaksin.getTotalPages(), vaksin.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<VaksinResponse> vaksinResponses = vaksin.map(asResponse -> {
            VaksinResponse vaksinResponse = new VaksinResponse();
            vaksinResponse.setIdVaksin(asResponse.getIdVaksin());
            vaksinResponse.setTanggalIB(asResponse.getTanggalIB());
            vaksinResponse.setLokasi(asResponse.getLokasi());
            vaksinResponse.setIdPeternak(asResponse.getIdPeternak());
            vaksinResponse.setKodeEartagNasional(asResponse.getKodeEartagNasional());
            vaksinResponse.setIb1(asResponse.getIb1());
            vaksinResponse.setIb2(asResponse.getIb2());
            vaksinResponse.setIb3(asResponse.getIb3());
            vaksinResponse.setIbLain(asResponse.getIbLain());
            vaksinResponse.setIdPejantan(asResponse.getIdPejantan());
            vaksinResponse.setIdPembuatan(asResponse.getIdPembuatan());
            vaksinResponse.setBangsaPejantan(asResponse.getBangsaPejantan());
            vaksinResponse.setProdusen(asResponse.getProdusen());
            vaksinResponse.setInseminator(asResponse.getInseminator());
            vaksinResponse.setCreatedAt(asResponse.getCreatedAt());
            vaksinResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return vaksinResponse;
        }).getContent();

        return new PagedResponse<>(vaksinResponses, vaksin.getNumber(),
                vaksin.getSize(), vaksin.getTotalElements(), vaksin.getTotalPages(), vaksin.isLast(), 200);
    }

    public Vaksin createVaksin(UserPrincipal currentUser, VaksinRequest vaksinRequest) {
        Vaksin vaksin = new Vaksin();
        vaksin.setIdVaksin(vaksinRequest.getIdVaksin());
        vaksin.setTanggalIB(vaksinRequest.getTanggalIB());
        vaksin.setLokasi(vaksinRequest.getLokasi());
        vaksin.setIdPeternak(vaksinRequest.getIdPeternak());
        vaksin.setKodeEartagNasional(vaksinRequest.getKodeEartagNasional());
        vaksin.setIb1(vaksinRequest.getIb1());
        vaksin.setIb2(vaksinRequest.getIb2());
        vaksin.setIb3(vaksinRequest.getIb3());
        vaksin.setIbLain(vaksinRequest.getIbLain());
        vaksin.setIdPejantan(vaksinRequest.getIdPejantan());
        vaksin.setIdPembuatan(vaksinRequest.getIdPembuatan());
        vaksin.setBangsaPejantan(vaksinRequest.getBangsaPejantan());
        vaksin.setProdusen(vaksinRequest.getProdusen());
        vaksin.setInseminator(vaksinRequest.getInseminator());
        vaksin.setCreatedBy(currentUser.getId());
        vaksin.setUpdatedBy(currentUser.getId());
        return vaksinRepository.save(vaksin);
    }

    public VaksinResponse getVaksinById(String vaksinId) {
        Vaksin vaksin = vaksinRepository.findById(vaksinId).orElseThrow(
                () -> new ResourceNotFoundException("Vaksin", "id", vaksinId));

        VaksinResponse vaksinResponse = new VaksinResponse();
        vaksinResponse.setIdVaksin(vaksin.getIdVaksin());
        vaksinResponse.setTanggalIB(vaksin.getTanggalIB());
        vaksinResponse.setLokasi(vaksin.getLokasi());
        vaksinResponse.setIdPeternak(vaksin.getIdPeternak());
        vaksinResponse.setKodeEartagNasional(vaksin.getKodeEartagNasional());
        vaksinResponse.setIb1(vaksin.getIb1());
        vaksinResponse.setIb2(vaksin.getIb2());
        vaksinResponse.setIb3(vaksin.getIb3());
        vaksinResponse.setIbLain(vaksin.getIbLain());
        vaksinResponse.setIdPejantan(vaksin.getIdPejantan());
        vaksinResponse.setIdPembuatan(vaksin.getIdPembuatan());
        vaksinResponse.setBangsaPejantan(vaksin.getBangsaPejantan());
        vaksinResponse.setProdusen(vaksin.getProdusen());
        vaksinResponse.setInseminator(vaksin.getInseminator());
        vaksinResponse.setCreatedAt(vaksin.getCreatedAt());
        vaksinResponse.setUpdatedAt(vaksin.getUpdatedAt());
        return vaksinResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Vaksin updateVaksin(VaksinRequest vaksinReq, String id, UserPrincipal currentUser){
        return vaksinRepository.findById(id).map(vaksin -> {
            vaksin.setIdVaksin(vaksinReq.getIdVaksin());
            vaksin.setTanggalIB(vaksinReq.getTanggalIB());
            vaksin.setLokasi(vaksinReq.getLokasi());
            vaksin.setIdPeternak(vaksinReq.getIdPeternak());
            vaksin.setKodeEartagNasional(vaksinReq.getKodeEartagNasional());
            vaksin.setIb1(vaksinReq.getIb1());
            vaksin.setIb2(vaksinReq.getIb2());
            vaksin.setIb3(vaksinReq.getIb3());
            vaksin.setIbLain(vaksinReq.getIbLain());
            vaksin.setIdPejantan(vaksinReq.getIdPejantan());
            vaksin.setIdPembuatan(vaksinReq.getIdPembuatan());
            vaksin.setBangsaPejantan(vaksinReq.getBangsaPejantan());
            vaksin.setProdusen(vaksinReq.getProdusen());
            vaksin.setInseminator(vaksinReq.getInseminator());
            vaksin.setCreatedBy(currentUser.getId());
            vaksin.setUpdatedBy(currentUser.getId());
            return vaksinRepository.save(vaksin);
        }).orElseThrow(() -> new ResourceNotFoundException("Vaksin" , "id" , id));
    }

    public void deleteVaksinById(String idVaksin){
        Optional<Vaksin> vaksin = vaksinRepository.findById(idVaksin);
        if(vaksin.isPresent()){
            vaksinRepository.deleteById(idVaksin);
        }else{
            throw new ResourceNotFoundException("Vaksin", "id", idVaksin);
        }
    }
}