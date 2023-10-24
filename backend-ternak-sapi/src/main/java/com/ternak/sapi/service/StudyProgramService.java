package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.StudyProgram;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.studyProgram.StudyProgramRequest;
import com.ternak.sapi.payload.studyProgram.StudyProgramResponse;
import com.ternak.sapi.repository.StudyProgramRepository;
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
public class StudyProgramService {
    @Autowired
    private StudyProgramRepository studyProgramRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudyProgramService.class);

    public PagedResponse<StudyProgramResponse> getAllStudyProgram(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<StudyProgram> studyPrograms = studyProgramRepository.findAll(pageable);

        if(studyPrograms.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), studyPrograms.getNumber(),
                    studyPrograms.getSize(), studyPrograms.getTotalElements(), studyPrograms.getTotalPages(), studyPrograms.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<StudyProgramResponse> studyProgramResponses = studyPrograms.map(asResponse -> {
            StudyProgramResponse studyProgramResponse = new StudyProgramResponse();
            studyProgramResponse.setIdPeternak(asResponse.getIdPeternak());
            studyProgramResponse.setNikPeternak(asResponse.getNikPeternak());
            studyProgramResponse.setName(asResponse.getName());
            studyProgramResponse.setIdISIKHNAS(asResponse.getIdISIKHNAS());
            studyProgramResponse.setLokasi(asResponse.getLokasi());
             studyProgramResponse.setPetugasPendaftar(asResponse.getPetugasPendaftar());
            studyProgramResponse.setTanggalPendaftaran(asResponse.getTanggalPendaftaran());
            studyProgramResponse.setCreatedAt(asResponse.getCreatedAt());
            studyProgramResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return studyProgramResponse;
        }).getContent();

        return new PagedResponse<>(studyProgramResponses, studyPrograms.getNumber(),
                studyPrograms.getSize(), studyPrograms.getTotalElements(), studyPrograms.getTotalPages(), studyPrograms.isLast(), 200);
    }

    public StudyProgram createStudyProgram(UserPrincipal currentUser, StudyProgramRequest studyProgramRequest) {
        StudyProgram studyProgram = new StudyProgram();
        
        //Menambahkan atau memanggil function ID agar bisa input manual value ID
        studyProgram.setIdPeternak(studyProgramRequest.getIdPeternak());
        studyProgram.setNikPeternak(studyProgramRequest.getNikPeternak());
        studyProgram.setName(studyProgramRequest.getName());
        studyProgram.setIdISIKHNAS(studyProgramRequest.getIdISIKHNAS());
        studyProgram.setLokasi(studyProgramRequest.getLokasi());
        studyProgram.setPetugasPendaftar(studyProgramRequest.getPetugasPendaftar());
        studyProgram.setTanggalPendaftaran(studyProgramRequest.getTanggalPendaftaran());
        studyProgram.setCreatedBy(currentUser.getId());
        studyProgram.setUpdatedBy(currentUser.getId());
        return studyProgramRepository.save(studyProgram);
    }

    public StudyProgramResponse getStudyProgramById(String studyProgramId) {
        StudyProgram studyProgram = studyProgramRepository.findById(studyProgramId).orElseThrow(
                () -> new ResourceNotFoundException("StudyProgram", "id", studyProgramId));

        StudyProgramResponse studyProgramResponse = new StudyProgramResponse();
        studyProgramResponse.setIdPeternak(studyProgram.getIdPeternak());
        studyProgramResponse.setNikPeternak(studyProgram.getNikPeternak());
        studyProgramResponse.setName(studyProgram.getName());
        studyProgramResponse.setIdISIKHNAS(studyProgram.getIdISIKHNAS());
        studyProgramResponse.setLokasi(studyProgram.getLokasi());
        studyProgramResponse.setPetugasPendaftar(studyProgram.getPetugasPendaftar());
        studyProgramResponse.setTanggalPendaftaran(studyProgram.getTanggalPendaftaran());
        studyProgramResponse.setCreatedAt(studyProgram.getCreatedAt());
        studyProgramResponse.setUpdatedAt(studyProgram.getUpdatedAt());
        return studyProgramResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public StudyProgram updateStudyProgram(StudyProgramRequest studyProgramReq, String id, UserPrincipal currentUser){
        return studyProgramRepository.findById(id).map(studyProgram -> {
            studyProgram.setIdPeternak(studyProgramReq.getIdPeternak());
            studyProgram.setNikPeternak(studyProgramReq.getNikPeternak());
            studyProgram.setName(studyProgramReq.getName());
            studyProgram.setIdISIKHNAS(studyProgramReq.getIdISIKHNAS());
            studyProgram.setLokasi(studyProgramReq.getLokasi());
            studyProgram.setPetugasPendaftar(studyProgramReq.getPetugasPendaftar());
            studyProgram.setTanggalPendaftaran(studyProgramReq.getTanggalPendaftaran());
            studyProgram.setCreatedBy(currentUser.getId());
            studyProgram.setUpdatedBy(currentUser.getId());
            return studyProgramRepository.save(studyProgram);
        }).orElseThrow(() -> new ResourceNotFoundException("StudyProgram" , "id" , id));
    }

    public void deleteStudyProgramById(String id){
        Optional<StudyProgram> studyProgram = studyProgramRepository.findById(id);
        if(studyProgram.isPresent()){
            studyProgramRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("StudyProgram", "id", id);
        }
    }
}