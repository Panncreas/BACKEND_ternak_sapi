package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.exception.FileStorageException;
import com.ternak.sapi.model.Hewan;
//import com.doyatama.university.model.Subject;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.hewan.HewanRequest;
import com.ternak.sapi.payload.hewan.HewanResponse;
import com.ternak.sapi.repository.HewanRepository;
import com.ternak.sapi.property.FileStorageProperties;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.util.AppConstants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javax.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HewanService {
    @Autowired
    private HewanRepository hewanRepository;
    
    private final Path fileStorageLocation;
    
    private static final Logger logger = LoggerFactory.getLogger(HewanService.class);
    
    @Autowired
    public HewanService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

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
            hewanResponse.setFotoHewan(asResponse.getFotoHewan());
            hewanResponse.setCreatedAt(asResponse.getCreatedAt());
            hewanResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return hewanResponse;
        }).getContent();

        return new PagedResponse<>(hewanResponses, hewans.getNumber(),
                hewans.getSize(), hewans.getTotalElements(), hewans.getTotalPages(), hewans.isLast(), 200);
    }

    public Hewan createHewan(UserPrincipal currentUser, @Valid HewanRequest hewanRequest, MultipartFile file ) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            Hewan hewan = new Hewan();
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
            hewan.setFotoHewan(fileName);
            return hewanRepository.save(hewan);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
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
        hewanResponse.setFotoHewan(hewan.getFotoHewan());
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

    public Hewan updateHewan(HewanRequest hewanReq, String id,  UserPrincipal currentUser) {
       
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