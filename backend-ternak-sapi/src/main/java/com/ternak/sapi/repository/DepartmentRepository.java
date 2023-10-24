package com.ternak.sapi.repository;

import com.ternak.sapi.model.Department;
import com.ternak.sapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
//    Optional<Department> findByName(String username);
    Optional<Department> findById(String kodeEartagNasional);
}

