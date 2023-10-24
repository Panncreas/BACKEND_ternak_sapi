package com.ternak.sapi.repository;

import com.ternak.sapi.model.DataTernak;
import com.ternak.sapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataTernakRepository extends JpaRepository<DataTernak, String> {
//    Optional<Department> findByName(String username);
    Optional<DataTernak> findById(String id);
}

