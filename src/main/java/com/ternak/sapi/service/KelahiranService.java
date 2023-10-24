package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Kelahiran;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.kelahiran.KelahiranRequest;
import com.ternak.sapi.payload.kelahiran.KelahiranResponse;
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
import com.ternak.sapi.repository.KelahiranRepository;

@Service
public class KelahiranService {
    @Autowired
    private KelahiranRepository kelahiranRepository;

    private static final Logger logger = LoggerFactory.getLogger(KelahiranService.class);

    public PagedResponse<KelahiranResponse> getAllKelahiran(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Kelahiran> kelahirans = kelahiranRepository.findAll(pageable);

        if(kelahirans.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), kelahirans.getNumber(),
                    kelahirans.getSize(), kelahirans.getTotalElements(), kelahirans.getTotalPages(), kelahirans.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<KelahiranResponse> kelahiranResponses = kelahirans.map(asResponse -> {
            KelahiranResponse kelahiranResponse = new KelahiranResponse();
            kelahiranResponse.setIdKejadian(asResponse.getIdKejadian());
            kelahiranResponse.setTanggalLaporan(asResponse.getTanggalLaporan());
            kelahiranResponse.setTanggalLahir(asResponse.getTanggalLahir());
            kelahiranResponse.setLokasi(asResponse.getLokasi());
            kelahiranResponse.setNamaPeternak(asResponse.getNamaPeternak());
            kelahiranResponse.setIdPeternak(asResponse.getIdPeternak());
            kelahiranResponse.setKartuTernakInduk(asResponse.getKartuTernakInduk());
            kelahiranResponse.setEartagInduk(asResponse.getEartagInduk());
            kelahiranResponse.setIdHewanInduk(asResponse.getIdHewanInduk());
            kelahiranResponse.setSpesiesInduk(asResponse.getSpesiesInduk());
            kelahiranResponse.setIdPejantanStraw(asResponse.getIdPejantanStraw());
            kelahiranResponse.setIdBatchStraw(asResponse.getIdBatchStraw());
            kelahiranResponse.setProdusenStraw(asResponse.getProdusenStraw());
            kelahiranResponse.setSpesiesPejantan(asResponse.getSpesiesPejantan());
            kelahiranResponse.setJumlah(asResponse.getJumlah());
            kelahiranResponse.setKartuTernakAnak(asResponse.getKartuTernakAnak());
            kelahiranResponse.setEartagAnak(asResponse.getEartagAnak());
            kelahiranResponse.setIdHewanAnak(asResponse.getIdHewanAnak());
            kelahiranResponse.setJenisKelaminAnak(asResponse.getJenisKelaminAnak());
            kelahiranResponse.setKategori(asResponse.getKategori());
            kelahiranResponse.setPetugasPelapor(asResponse.getPetugasPelapor());
            kelahiranResponse.setUrutanIb(asResponse.getUrutanIb());
            kelahiranResponse.setCreatedAt(asResponse.getCreatedAt());
            kelahiranResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return kelahiranResponse;
        }).getContent();

        return new PagedResponse<>(kelahiranResponses, kelahirans.getNumber(),
                kelahirans.getSize(), kelahirans.getTotalElements(), kelahirans.getTotalPages(), kelahirans.isLast(), 200);
    }

    public Kelahiran createKelahiran(UserPrincipal currentUser, KelahiranRequest kelahiranRequest) {
        Kelahiran kelahiran = new Kelahiran();
        kelahiran.setIdKejadian(kelahiranRequest.getIdKejadian());
        kelahiran.setTanggalLaporan(kelahiranRequest.getTanggalLaporan());
        kelahiran.setTanggalLahir(kelahiranRequest.getTanggalLahir());
        kelahiran.setLokasi(kelahiranRequest.getLokasi());
        kelahiran.setNamaPeternak(kelahiranRequest.getNamaPeternak());
        kelahiran.setIdPeternak(kelahiranRequest.getIdPeternak());
        kelahiran.setKartuTernakInduk(kelahiranRequest.getKartuTernakInduk());
        kelahiran.setEartagInduk(kelahiranRequest.getEartagInduk());
        kelahiran.setIdHewanInduk(kelahiranRequest.getIdHewanInduk());
        kelahiran.setSpesiesInduk(kelahiranRequest.getSpesiesInduk());
        kelahiran.setIdPejantanStraw(kelahiranRequest.getIdPejantanStraw());
        kelahiran.setIdBatchStraw(kelahiranRequest.getIdBatchStraw());
        kelahiran.setProdusenStraw(kelahiranRequest.getProdusenStraw());
        kelahiran.setSpesiesPejantan(kelahiranRequest.getSpesiesPejantan());
        kelahiran.setJumlah(kelahiranRequest.getJumlah());
        kelahiran.setKartuTernakAnak(kelahiranRequest.getKartuTernakAnak());
        kelahiran.setEartagAnak(kelahiranRequest.getEartagAnak());
        kelahiran.setIdHewanAnak(kelahiranRequest.getIdHewanAnak());
        kelahiran.setJenisKelaminAnak(kelahiranRequest.getJenisKelaminAnak());
        kelahiran.setKategori(kelahiranRequest.getKategori());
        kelahiran.setPetugasPelapor(kelahiranRequest.getPetugasPelapor());
        kelahiran.setUrutanIb(kelahiranRequest.getUrutanIb());
        kelahiran.setCreatedBy(currentUser.getId());
        kelahiran.setUpdatedBy(currentUser.getId());
        return kelahiranRepository.save(kelahiran);
    }

    public KelahiranResponse getKelahiranById(String kelahiranId) {
        Kelahiran kelahiran = kelahiranRepository.findById(kelahiranId).orElseThrow(
                () -> new ResourceNotFoundException("Kelahiran", "id", kelahiranId));

        KelahiranResponse kelahiranResponse = new KelahiranResponse();
        kelahiranResponse.setIdKejadian(kelahiran.getIdKejadian());
        kelahiranResponse.setTanggalLaporan(kelahiran.getTanggalLaporan());
        kelahiranResponse.setTanggalLahir(kelahiran.getTanggalLahir());
        kelahiranResponse.setLokasi(kelahiran.getLokasi());
        kelahiranResponse.setNamaPeternak(kelahiran.getNamaPeternak());
        kelahiranResponse.setIdPeternak(kelahiran.getIdPeternak());
        kelahiranResponse.setKartuTernakInduk(kelahiran.getKartuTernakInduk());
        kelahiranResponse.setEartagInduk(kelahiran.getEartagInduk());
        kelahiranResponse.setIdHewanInduk(kelahiran.getIdHewanInduk());
        kelahiranResponse.setSpesiesInduk(kelahiran.getSpesiesInduk());
        kelahiranResponse.setIdPejantanStraw(kelahiran.getIdPejantanStraw());
        kelahiranResponse.setIdBatchStraw(kelahiran.getIdBatchStraw());
        kelahiranResponse.setProdusenStraw(kelahiran.getProdusenStraw());
        kelahiranResponse.setSpesiesPejantan(kelahiran.getSpesiesPejantan());
        kelahiranResponse.setJumlah(kelahiran.getJumlah());
        kelahiranResponse.setKartuTernakAnak(kelahiran.getKartuTernakAnak());
        kelahiranResponse.setEartagAnak(kelahiran.getEartagAnak());
        kelahiranResponse.setIdHewanAnak(kelahiran.getIdHewanAnak());
        kelahiranResponse.setJenisKelaminAnak(kelahiran.getJenisKelaminAnak());
        kelahiranResponse.setKategori(kelahiran.getKategori());
        kelahiranResponse.setPetugasPelapor(kelahiran.getPetugasPelapor());
        kelahiranResponse.setUrutanIb(kelahiran.getUrutanIb());
        kelahiranResponse.setCreatedAt(kelahiran.getCreatedAt());
        kelahiranResponse.setUpdatedAt(kelahiran.getUpdatedAt());
        return kelahiranResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Kelahiran updateKelahiran(KelahiranRequest kelahiranReq, String idKejadian, UserPrincipal currentUser){
        return kelahiranRepository.findById(idKejadian).map(kelahiran -> {
            kelahiran.setIdKejadian(kelahiranReq.getIdKejadian());
            kelahiran.setTanggalLaporan(kelahiranReq.getTanggalLaporan());
            kelahiran.setTanggalLahir(kelahiranReq.getTanggalLahir());
            kelahiran.setLokasi(kelahiranReq.getLokasi());
            kelahiran.setNamaPeternak(kelahiranReq.getNamaPeternak());
            kelahiran.setIdPeternak(kelahiranReq.getIdPeternak());
            kelahiran.setKartuTernakInduk(kelahiranReq.getKartuTernakInduk());
            kelahiran.setEartagInduk(kelahiranReq.getEartagInduk());
            kelahiran.setIdHewanInduk(kelahiranReq.getIdHewanInduk());
            kelahiran.setSpesiesInduk(kelahiranReq.getSpesiesInduk());
            kelahiran.setIdPejantanStraw(kelahiranReq.getIdPejantanStraw());
            kelahiran.setIdBatchStraw(kelahiranReq.getIdBatchStraw());
            kelahiran.setProdusenStraw(kelahiranReq.getProdusenStraw());
            kelahiran.setSpesiesPejantan(kelahiranReq.getSpesiesPejantan());
            kelahiran.setJumlah(kelahiranReq.getJumlah());
            kelahiran.setKartuTernakAnak(kelahiranReq.getKartuTernakAnak());
            kelahiran.setEartagAnak(kelahiranReq.getEartagAnak());
            kelahiran.setIdHewanAnak(kelahiranReq.getIdHewanAnak());
            kelahiran.setJenisKelaminAnak(kelahiranReq.getJenisKelaminAnak());
            kelahiran.setKategori(kelahiranReq.getKategori());
            kelahiran.setPetugasPelapor(kelahiranReq.getPetugasPelapor());
            kelahiran.setUrutanIb(kelahiranReq.getUrutanIb());
            kelahiran.setUpdatedBy(currentUser.getId());
            return kelahiranRepository.save(kelahiran);
        }).orElseThrow(() -> new ResourceNotFoundException("Kelahiran" , "idKejadian" , idKejadian));
    }

    public void deleteKelahiranById(String idKejadian){
        Optional<Kelahiran> kelahiran = kelahiranRepository.findById(idKejadian);
        if(kelahiran.isPresent()){
            kelahiranRepository.deleteById(idKejadian);
        }else{
            throw new ResourceNotFoundException("Kelahiran", "idKejadian", idKejadian);
        }
    }
}