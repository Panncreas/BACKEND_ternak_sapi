package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Hewan;
//import com.doyatama.university.model.Subject;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.hewan.HewanRequest;
import com.ternak.sapi.payload.hewan.HewanResponse;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.util.AppConstants;
import java.io.File;
import java.io.IOException;
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
import java.util.UUID;
import java.util.logging.Level;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HewanService {
    @Autowired
    private HewanRepository hewanRepository;

    private static final Logger logger = LoggerFactory.getLogger(HewanService.class);
    private final String FOLDER_PATH="E:/Myfile/Magang Maching Fund/img";

    public PagedResponse<HewanResponse> getAllHewan(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Hewan> hewans = hewanRepository.findAll(pageable);

        if(hewans.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), hewans.getNumber(),
                    hewans.getSize(), hewans.getTotalElements(), hewans.getTotalPages(), hewans.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<HewanResponse> hewanResponses = hewans.map(asResponse -> {
            HewanResponse hewanResponse = new HewanResponse();
            hewanResponse.setNoKartuTernak(asResponse.getNoKartuTernak());
            hewanResponse.setKodeEartagNasional(asResponse.getKodeEartagNasional());
            hewanResponse.setProvinsi(asResponse.getProvinsi());
            hewanResponse.setKabupaten(asResponse.getKabupaten());
            hewanResponse.setKecamatan(asResponse.getKecamatan());
            hewanResponse.setDesa(asResponse.getDesa());
            hewanResponse.setNamaPeternak(asResponse.getNamaPeternak());
            hewanResponse.setIdPeternak(asResponse.getIdPeternak());
            hewanResponse.setNikPeternak(asResponse.getNikPeternak());
            hewanResponse.setSpesies(asResponse.getSpesies());
            hewanResponse.setSex(asResponse.getSex());
            hewanResponse.setUmur(asResponse.getUmur());
            hewanResponse.setIdentifikasiHewan(asResponse.getIdentifikasiHewan());
            hewanResponse.setPetugasPendaftar(asResponse.getPetugasPendaftar());
            hewanResponse.setTanggalTerdaftar(asResponse.getTanggalTerdaftar());
            hewanResponse.setImage(asResponse.getImage());
            hewanResponse.setCreatedAt(asResponse.getCreatedAt());
            hewanResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return hewanResponse;
        }).getContent();

        return new PagedResponse<>(hewanResponses, hewans.getNumber(),
                hewans.getSize(), hewans.getTotalElements(), hewans.getTotalPages(), hewans.isLast(), 200);
    }

    public Hewan createHewan(UserPrincipal currentUser, MultipartFile file, HewanRequest hewanRequest) throws IOException {
        Hewan hewan = new Hewan();
        String originalFilename = file.getOriginalFilename();
        String randomName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(originalFilename);
        String filePath = FOLDER_PATH + randomName;
        File destFile = new File(FOLDER_PATH, randomName);
        
        hewan.setKodeEartagNasional(hewanRequest.getKodeEartagNasional());
        hewan.setNoKartuTernak(hewanRequest.getNoKartuTernak());
        hewan.setProvinsi(hewanRequest.getProvinsi());
        hewan.setKabupaten(hewanRequest.getKabupaten());
        hewan.setKecamatan(hewanRequest.getKecamatan());
        hewan.setDesa(hewanRequest.getDesa());
        hewan.setNamaPeternak(hewanRequest.getNamaPeternak());
        hewan.setIdPeternak(hewanRequest.getIdPeternak());
        hewan.setNikPeternak(hewanRequest.getNikPeternak());
        hewan.setSpesies(hewanRequest.getSpesies());
        hewan.setSex(hewanRequest.getSex());
        hewan.setUmur(hewanRequest.getUmur());
        hewan.setIdentifikasiHewan(hewanRequest.getIdentifikasiHewan());
        hewan.setPetugasPendaftar(hewanRequest.getPetugasPendaftar());
        hewan.setTanggalTerdaftar(hewanRequest.getTanggalTerdaftar()); 
        hewan.setCreatedBy(currentUser.getId());
        hewan.setUpdatedBy(currentUser.getId());
        file.transferTo(destFile);
        hewan.setImage(filePath);
        return hewanRepository.save(hewan);
    }

    public HewanResponse getHewanById(String hewanId) {
        Hewan hewan = hewanRepository.findById(hewanId).orElseThrow(
                () -> new ResourceNotFoundException("Hewan", "id", hewanId));

        HewanResponse hewanResponse = new HewanResponse();
        hewanResponse.setNoKartuTernak(hewan.getNoKartuTernak());
        hewanResponse.setKodeEartagNasional(hewan.getKodeEartagNasional());
        hewanResponse.setProvinsi(hewan.getProvinsi());
        hewanResponse.setKabupaten(hewan.getKabupaten());
        hewanResponse.setKecamatan(hewan.getKecamatan());
        hewanResponse.setDesa(hewan.getDesa());
        hewanResponse.setNamaPeternak(hewan.getNamaPeternak());
        hewanResponse.setIdPeternak(hewan.getIdPeternak());
        hewanResponse.setNikPeternak(hewan.getNikPeternak());
        hewanResponse.setSpesies(hewan.getSpesies());
        hewanResponse.setSex(hewan.getSex());
        hewanResponse.setUmur(hewan.getUmur());
        hewanResponse.setIdentifikasiHewan(hewan.getIdentifikasiHewan());
        hewanResponse.setPetugasPendaftar(hewan.getPetugasPendaftar());
        hewanResponse.setTanggalTerdaftar(hewan.getTanggalTerdaftar());
        hewanResponse.setImage(hewan.getImage());
        hewanResponse.setCreatedAt(hewan.getCreatedAt());
        hewanResponse.setUpdatedAt(hewan.getUpdatedAt());
        return hewanResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Hewan updateHewan(HewanRequest hewanReq, String id, MultipartFile file, UserPrincipal currentUser) {
        // if else exist or not
        String originalFilename = file.getOriginalFilename();
        String randomName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(originalFilename);
        String filePath = FOLDER_PATH + randomName;
        File destFile = new File(FOLDER_PATH, randomName);
        return hewanRepository.findById(id).map(hewan -> {
            hewan.setNoKartuTernak(hewanReq.getNoKartuTernak());
            hewan.setKodeEartagNasional(hewanReq.getKodeEartagNasional());
            hewan.setProvinsi(hewanReq.getProvinsi());
            hewan.setKabupaten(hewanReq.getKabupaten());
            hewan.setKecamatan(hewanReq.getKecamatan());
            hewan.setDesa(hewanReq.getDesa());
            hewan.setNamaPeternak(hewanReq.getNamaPeternak());
            hewan.setIdPeternak(hewanReq.getIdPeternak());
            hewan.setNikPeternak(hewanReq.getNikPeternak());
            hewan.setSpesies(hewanReq.getSpesies());
            hewan.setSex(hewanReq.getSex());
            hewan.setUmur(hewanReq.getUmur());
            hewan.setIdentifikasiHewan(hewanReq.getIdentifikasiHewan());
            hewan.setPetugasPendaftar(hewanReq.getPetugasPendaftar());
            hewan.setTanggalTerdaftar(hewanReq.getTanggalTerdaftar());
            try {
                file.transferTo(destFile);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(HewanService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                java.util.logging.Logger.getLogger(HewanService.class.getName()).log(Level.SEVERE, null, ex);
            }
            hewan.setImage(filePath);
            hewan.setUpdatedBy(currentUser.getId());
            return hewanRepository.save(hewan);
        }).orElseThrow(() -> new ResourceNotFoundException("Hewan" , "id" , id));
    }

    public void deleteHewanById(String id){
        Optional<Hewan> hewan = hewanRepository.findById(id);
        if(hewan.isPresent()){
            hewanRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Hewan", "id", id);
        }
    }
}