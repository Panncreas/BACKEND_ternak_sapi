package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Inseminasi;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.inseminasi.InseminasiRequest;
import com.ternak.sapi.payload.inseminasi.InseminasiResponse;
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
import com.ternak.sapi.repository.InseminasiRepository;

@Service
public class InseminasiService {
    @Autowired
    private InseminasiRepository inseminasiRepository;

    private static final Logger logger = LoggerFactory.getLogger(InseminasiService.class);

    public PagedResponse<InseminasiResponse> getAllInseminasi(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Inseminasi> inseminasi = inseminasiRepository.findAll(pageable);

        if(inseminasi.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), inseminasi.getNumber(),
                    inseminasi.getSize(), inseminasi.getTotalElements(), inseminasi.getTotalPages(), inseminasi.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<InseminasiResponse> inseminasiResponses = inseminasi.map(asResponse -> {
            InseminasiResponse inseminasiResponse = new InseminasiResponse();
            inseminasiResponse.setIdInseminasi(asResponse.getIdInseminasi());
            inseminasiResponse.setTanggalIB(asResponse.getTanggalIB());
            inseminasiResponse.setLokasi(asResponse.getLokasi());
            inseminasiResponse.setNamaPeternak(asResponse.getNamaPeternak());
            inseminasiResponse.setIdPeternak(asResponse.getIdPeternak());
            inseminasiResponse.setIdHewan(asResponse.getIdHewan());
            inseminasiResponse.setEartag(asResponse.getEartag());
            inseminasiResponse.setIb1(asResponse.getIb1());
            inseminasiResponse.setIb2(asResponse.getIb2());
            inseminasiResponse.setIb3(asResponse.getIb3());
            inseminasiResponse.setIbLain(asResponse.getIbLain());
            inseminasiResponse.setIdPejantan(asResponse.getIdPejantan());
            inseminasiResponse.setIdPembuatan(asResponse.getIdPembuatan());
            inseminasiResponse.setBangsaPejantan(asResponse.getBangsaPejantan());
            inseminasiResponse.setProdusen(asResponse.getProdusen());
            inseminasiResponse.setInseminator(asResponse.getInseminator());
            inseminasiResponse.setCreatedAt(asResponse.getCreatedAt());
            inseminasiResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return inseminasiResponse;
        }).getContent();

        return new PagedResponse<>(inseminasiResponses, inseminasi.getNumber(),
                inseminasi.getSize(), inseminasi.getTotalElements(), inseminasi.getTotalPages(), inseminasi.isLast(), 200);
    }

    public Inseminasi createInseminasi(UserPrincipal currentUser, InseminasiRequest inseminasiRequest) {
        Inseminasi inseminasi = new Inseminasi();
        inseminasi.setIdInseminasi(inseminasiRequest.getIdInseminasi());
        inseminasi.setTanggalIB(inseminasiRequest.getTanggalIB());
        inseminasi.setLokasi(inseminasiRequest.getLokasi());
        inseminasi.setNamaPeternak(inseminasiRequest.getNamaPeternak());
        inseminasi.setIdPeternak(inseminasiRequest.getIdPeternak());
        inseminasi.setIdHewan(inseminasiRequest.getIdHewan());
        inseminasi.setEartag(inseminasiRequest.getEartag());
        inseminasi.setIb1(inseminasiRequest.getIb1());
        inseminasi.setIb2(inseminasiRequest.getIb2());
        inseminasi.setIb3(inseminasiRequest.getIb3());
        inseminasi.setIbLain(inseminasiRequest.getIbLain());
        inseminasi.setIdPejantan(inseminasiRequest.getIdPejantan());
        inseminasi.setIdPembuatan(inseminasiRequest.getIdPembuatan());
        inseminasi.setBangsaPejantan(inseminasiRequest.getBangsaPejantan());
        inseminasi.setProdusen(inseminasiRequest.getProdusen());
        inseminasi.setInseminator(inseminasiRequest.getInseminator());
        inseminasi.setCreatedBy(currentUser.getId());
        inseminasi.setUpdatedBy(currentUser.getId());
        return inseminasiRepository.save(inseminasi);
    }

    public InseminasiResponse getInseminasiById(String inseminasiId) {
        Inseminasi inseminasi = inseminasiRepository.findById(inseminasiId).orElseThrow(
                () -> new ResourceNotFoundException("Inseminasi", "id", inseminasiId));

        InseminasiResponse inseminasiResponse = new InseminasiResponse();
        inseminasiResponse.setIdInseminasi(inseminasi.getIdInseminasi());
        inseminasiResponse.setTanggalIB(inseminasi.getTanggalIB());
        inseminasiResponse.setLokasi(inseminasi.getLokasi());
        inseminasiResponse.setNamaPeternak(inseminasi.getNamaPeternak());
        inseminasiResponse.setIdPeternak(inseminasi.getIdPeternak());
        inseminasiResponse.setIdHewan(inseminasi.getIdHewan());
        inseminasiResponse.setEartag(inseminasi.getEartag());
        inseminasiResponse.setIb1(inseminasi.getIb1());
        inseminasiResponse.setIb2(inseminasi.getIb2());
        inseminasiResponse.setIb3(inseminasi.getIb3());
        inseminasiResponse.setIbLain(inseminasi.getIbLain());
        inseminasiResponse.setIdPejantan(inseminasi.getIdPejantan());
        inseminasiResponse.setIdPembuatan(inseminasi.getIdPembuatan());
        inseminasiResponse.setBangsaPejantan(inseminasi.getBangsaPejantan());
        inseminasiResponse.setProdusen(inseminasi.getProdusen());
        inseminasiResponse.setInseminator(inseminasi.getInseminator());
        inseminasiResponse.setCreatedAt(inseminasi.getCreatedAt());
        inseminasiResponse.setUpdatedAt(inseminasi.getUpdatedAt());
        return inseminasiResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Inseminasi updateInseminasi(InseminasiRequest inseminasiReq, String id, UserPrincipal currentUser){
        return inseminasiRepository.findById(id).map(inseminasi -> {
            inseminasi.setIdInseminasi(inseminasiReq.getIdInseminasi());
            inseminasi.setTanggalIB(inseminasiReq.getTanggalIB());
            inseminasi.setLokasi(inseminasiReq.getLokasi());
            inseminasi.setNamaPeternak(inseminasiReq.getNamaPeternak());
            inseminasi.setIdPeternak(inseminasiReq.getIdPeternak());
            inseminasi.setIdHewan(inseminasiReq.getIdHewan());
            inseminasi.setEartag(inseminasiReq.getEartag());
            inseminasi.setIb1(inseminasiReq.getIb1());
            inseminasi.setIb2(inseminasiReq.getIb2());
            inseminasi.setIb3(inseminasiReq.getIb3());
            inseminasi.setIbLain(inseminasiReq.getIbLain());
            inseminasi.setIdPejantan(inseminasiReq.getIdPejantan());
            inseminasi.setIdPembuatan(inseminasiReq.getIdPembuatan());
            inseminasi.setBangsaPejantan(inseminasiReq.getBangsaPejantan());
            inseminasi.setProdusen(inseminasiReq.getProdusen());
            inseminasi.setInseminator(inseminasiReq.getInseminator());
            inseminasi.setCreatedBy(currentUser.getId());
            inseminasi.setUpdatedBy(currentUser.getId());
            return inseminasiRepository.save(inseminasi);
        }).orElseThrow(() -> new ResourceNotFoundException("Inseminasi" , "id" , id));
    }

    public void deleteInseminasiById(String idInseminasi){
        Optional<Inseminasi> inseminasi = inseminasiRepository.findById(idInseminasi);
        if(inseminasi.isPresent()){
            inseminasiRepository.deleteById(idInseminasi);
        }else{
            throw new ResourceNotFoundException("Inseminasi", "id", idInseminasi);
        }
    }
}