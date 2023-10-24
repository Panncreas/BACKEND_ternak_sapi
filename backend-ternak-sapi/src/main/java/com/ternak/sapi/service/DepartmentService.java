package com.ternak.sapi.service;

import com.ternak.sapi.exception.BadRequestException;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Department;
//import com.doyatama.university.model.Subject;
import com.ternak.sapi.payload.PagedResponse;
import com.ternak.sapi.payload.department.DepartmentRequest;
import com.ternak.sapi.payload.department.DepartmentResponse;
import com.ternak.sapi.repository.DepartmentRepository;
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
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    public PagedResponse<DepartmentResponse> getAllDepartment(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Department> departments = departmentRepository.findAll(pageable);

        if(departments.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), departments.getNumber(),
                    departments.getSize(), departments.getTotalElements(), departments.getTotalPages(), departments.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<DepartmentResponse> departmentResponses = departments.map(asResponse -> {
            DepartmentResponse departmentResponse = new DepartmentResponse();
            departmentResponse.setNoKartuTernak(asResponse.getNoKartuTernak());
            departmentResponse.setKodeEartagNasional(asResponse.getKodeEartagNasional());
            departmentResponse.setProvinsi(asResponse.getProvinsi());
            departmentResponse.setKabupaten(asResponse.getKabupaten());
            departmentResponse.setKecamatan(asResponse.getKecamatan());
            departmentResponse.setDesa(asResponse.getDesa());
            departmentResponse.setNamaPeternak(asResponse.getNamaPeternak());
            departmentResponse.setIdPeternak(asResponse.getIdPeternak());
            departmentResponse.setNikPeternak(asResponse.getNikPeternak());
            departmentResponse.setSpesies(asResponse.getSpesies());
            departmentResponse.setSex(asResponse.getSex());
            departmentResponse.setUmur(asResponse.getUmur());
            departmentResponse.setIdentifikasiHewan(asResponse.getIdentifikasiHewan());
            departmentResponse.setPetugasPendaftar(asResponse.getPetugasPendaftar());
            departmentResponse.setTanggalTerdaftar(asResponse.getTanggalTerdaftar());
            departmentResponse.setCreatedAt(asResponse.getCreatedAt());
            departmentResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return departmentResponse;
        }).getContent();

        return new PagedResponse<>(departmentResponses, departments.getNumber(),
                departments.getSize(), departments.getTotalElements(), departments.getTotalPages(), departments.isLast(), 200);
    }

    public Department createDepartment(UserPrincipal currentUser, DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setNoKartuTernak(departmentRequest.getNoKartuTernak());
        department.setKodeEartagNasional(departmentRequest.getKodeEartagNasional());
        department.setProvinsi(departmentRequest.getProvinsi());
        department.setKabupaten(departmentRequest.getKabupaten());
        department.setKecamatan(departmentRequest.getKecamatan());
        department.setDesa(departmentRequest.getDesa());
        department.setNamaPeternak(departmentRequest.getNamaPeternak());
        department.setIdPeternak(departmentRequest.getIdPeternak());
        department.setNikPeternak(departmentRequest.getNikPeternak());
        department.setSpesies(departmentRequest.getSpesies());
        department.setSex(departmentRequest.getSex());
        department.setUmur(departmentRequest.getUmur());
        department.setIdentifikasiHewan(departmentRequest.getIdentifikasiHewan());
        department.setPetugasPendaftar(departmentRequest.getPetugasPendaftar());
        department.setTanggalTerdaftar(departmentRequest.getTanggalTerdaftar()); 
        department.setCreatedBy(currentUser.getId());
        department.setUpdatedBy(currentUser.getId());
        return departmentRepository.save(department);
    }

    public DepartmentResponse getDepartmentById(String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", departmentId));

        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setNoKartuTernak(department.getNoKartuTernak());
        departmentResponse.setKodeEartagNasional(department.getKodeEartagNasional());
        departmentResponse.setProvinsi(department.getProvinsi());
        departmentResponse.setKabupaten(department.getKabupaten());
        departmentResponse.setKecamatan(department.getKecamatan());
        departmentResponse.setDesa(department.getDesa());
        departmentResponse.setNamaPeternak(department.getNamaPeternak());
        departmentResponse.setIdPeternak(department.getIdPeternak());
        departmentResponse.setNikPeternak(department.getNikPeternak());
        departmentResponse.setSpesies(department.getSpesies());
        departmentResponse.setSex(department.getSex());
        departmentResponse.setUmur(department.getUmur());
        departmentResponse.setIdentifikasiHewan(department.getIdentifikasiHewan());
        departmentResponse.setPetugasPendaftar(department.getPetugasPendaftar());
        departmentResponse.setTanggalTerdaftar(department.getTanggalTerdaftar());
        departmentResponse.setCreatedAt(department.getCreatedAt());
        departmentResponse.setUpdatedAt(department.getUpdatedAt());
        return departmentResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Department updateDepartment(DepartmentRequest departmentReq, String id, UserPrincipal currentUser){
        return departmentRepository.findById(id).map(department -> {
            department.setNoKartuTernak(departmentReq.getNoKartuTernak());
            department.setKodeEartagNasional(departmentReq.getKodeEartagNasional());
            department.setProvinsi(departmentReq.getProvinsi());
            department.setKabupaten(departmentReq.getKabupaten());
            department.setKecamatan(departmentReq.getKecamatan());
            department.setDesa(departmentReq.getDesa());
            department.setNamaPeternak(departmentReq.getNamaPeternak());
            department.setIdPeternak(departmentReq.getIdPeternak());
            department.setNikPeternak(departmentReq.getNikPeternak());
            department.setSpesies(departmentReq.getSpesies());
            department.setSex(departmentReq.getSex());
            department.setUmur(departmentReq.getUmur());
            department.setIdentifikasiHewan(departmentReq.getIdentifikasiHewan());
            department.setPetugasPendaftar(departmentReq.getPetugasPendaftar());
            department.setTanggalTerdaftar(departmentReq.getTanggalTerdaftar());
            department.setUpdatedBy(currentUser.getId());
            return departmentRepository.save(department);
        }).orElseThrow(() -> new ResourceNotFoundException("Department" , "id" , id));
    }

    public void deleteDepartmentById(String id){
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            departmentRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Department", "id", id);
        }
    }
}