package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.FileStorageException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.kandang.KandangRequest;
import com.ternak.sapi.payload.kandang.KandangResponse;
import com.ternak.sapi.property.FileStorageProperties;
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
import com.ternak.sapi.repository.KandangRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class KandangService {
    @Autowired
    private KandangRepository kandangRepository;
    private final Path fileStorageLocation;
    private static final Logger logger = LoggerFactory.getLogger(KandangService.class);
    
    @Autowired
    public KandangService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public PagedResponse<KandangResponse> getAllKandang(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Kandang> kandang = kandangRepository.findAll(pageable);

        if(kandang.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), kandang.getNumber(),
                    kandang.getSize(), kandang.getTotalElements(), kandang.getTotalPages(), kandang.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<KandangResponse> kandangResponses = kandang.map(asResponse -> {
            KandangResponse kandangResponse = new KandangResponse();
            kandangResponse.setIdKandang(asResponse.getIdKandang());
            kandangResponse.setIdPeternak(asResponse.getIdPeternak());
            kandangResponse.setLuas(asResponse.getLuas());
            kandangResponse.setKapasitas(asResponse.getKapasitas());
            kandangResponse.setNilaiBangunan(asResponse.getNilaiBangunan());
            kandangResponse.setAlamat(asResponse.getAlamat());
            kandangResponse.setDesa(asResponse.getDesa());
            kandangResponse.setKecamatan(asResponse.getKecamatan());
            kandangResponse.setKabupaten(asResponse.getKabupaten());
            kandangResponse.setProvinsi(asResponse.getProvinsi());
            kandangResponse.setFotoKandang(asResponse.getFotoKandang());
            kandangResponse.setFotoType(asResponse.getFotoType());
            kandangResponse.setData(asResponse.getData());
            kandangResponse.setLatitude(asResponse.getLatitude());
            kandangResponse.setLongitude(asResponse.getLongitude());
            kandangResponse.setCreatedAt(asResponse.getCreatedAt());
            kandangResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return kandangResponse;
        }).getContent();

        return new PagedResponse<>(kandangResponses, kandang.getNumber(),
                kandang.getSize(), kandang.getTotalElements(), kandang.getTotalPages(), kandang.isLast(), 200);
    }

    public Kandang createKandang(UserPrincipal currentUser, @Valid KandangRequest kandangRequest, MultipartFile file) throws IOException {
        Kandang kandang = new Kandang();
        kandang.setIdKandang(kandangRequest.getIdKandang());
        kandang.setIdPeternak(kandangRequest.getIdPeternak());
        kandang.setLuas(kandangRequest.getLuas());
        kandang.setKapasitas(kandangRequest.getKapasitas());
        kandang.setNilaiBangunan(kandangRequest.getNilaiBangunan());
        kandang.setAlamat(kandangRequest.getAlamat());
        kandang.setDesa(kandangRequest.getDesa());
        kandang.setKecamatan(kandangRequest.getKecamatan());
        kandang.setKabupaten(kandangRequest.getKabupaten());
        kandang.setProvinsi(kandangRequest.getProvinsi());
        kandang.setCreatedBy(currentUser.getId());
        kandang.setUpdatedBy(currentUser.getId());
        kandang.setLatitude(kandangRequest.getLatitude());
        kandang.setLongitude(kandangRequest.getLongitude());

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                // Check if the file's name contains invalid characters
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }

                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                kandang.setFotoKandang(fileName);
                kandang.setFotoType(file.getContentType());
                kandang.setData(file.getBytes());
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }
        }

        return kandangRepository.save(kandang);
    }


    public KandangResponse getKandangById(String kandangId) {
        Kandang kandang = kandangRepository.findById(kandangId).orElseThrow(
                () -> new ResourceNotFoundException("Kandang", "id", kandangId));

        KandangResponse kandangResponse = new KandangResponse();
        kandangResponse.setIdKandang(kandang.getIdKandang());
        kandangResponse.setIdPeternak(kandang.getIdPeternak());
        kandangResponse.setLuas(kandang.getLuas());
        kandangResponse.setKapasitas(kandang.getKapasitas());
        kandangResponse.setNilaiBangunan(kandang.getNilaiBangunan());
        kandangResponse.setAlamat(kandang.getAlamat());
        kandangResponse.setDesa(kandang.getDesa());
        kandangResponse.setKecamatan(kandang.getKecamatan());
        kandangResponse.setKabupaten(kandang.getKabupaten());
        kandangResponse.setProvinsi(kandang.getProvinsi());
        kandangResponse.setFotoKandang(kandang.getFotoKandang());
        kandangResponse.setLatitude(kandang.getLatitude());
        kandangResponse.setLongitude(kandang.getLongitude());
        kandangResponse.setCreatedAt(kandang.getCreatedAt());
        kandangResponse.setUpdatedAt(kandang.getUpdatedAt());
        return kandangResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Kandang updateKandang(KandangRequest kandangReq, String id, UserPrincipal currentUser, MultipartFile file) throws IOException{
        return kandangRepository.findById(id).map(kandang -> {
            if (file != null && !file.isEmpty()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                try {
                    // Check if the file's name contains invalid characters
                    if (fileName.contains("..")) {
                        throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                    }

                    Path targetLocation = this.fileStorageLocation.resolve(fileName);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                    kandang.setFotoKandang(fileName);
                    kandang.setFotoType(file.getContentType());
                    kandang.setData(file.getBytes());
                } catch (IOException ex) {
                    throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
                }
            }
            
            kandang.setIdKandang(kandangReq.getIdKandang());
            kandang.setIdPeternak(kandangReq.getIdPeternak());
            kandang.setLuas(kandangReq.getLuas());
            kandang.setKapasitas(kandangReq.getKapasitas());
            kandang.setNilaiBangunan(kandangReq.getNilaiBangunan());
            kandang.setAlamat(kandangReq.getAlamat());
            kandang.setDesa(kandangReq.getDesa());
            kandang.setKecamatan(kandangReq.getKecamatan());
            kandang.setKabupaten(kandangReq.getKabupaten());
            kandang.setProvinsi(kandangReq.getProvinsi());
            kandang.setLatitude(kandangReq.getLatitude());
            kandang.setLongitude(kandangReq.getLongitude());
            kandang.setCreatedBy(currentUser.getId());
            kandang.setUpdatedBy(currentUser.getId());
            return kandangRepository.save(kandang);
        }).orElseThrow(() -> new ResourceNotFoundException("Kandang" , "id" , id));
    }

    public void deleteKandangById(String id){
        Optional<Kandang> kandang = kandangRepository.findById(id);
        if(kandang.isPresent()){
            kandangRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Kandang", "id", id);
        }
    }
}