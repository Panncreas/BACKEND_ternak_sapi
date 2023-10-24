package com.ternak.sapi.repository;


import com.ternak.sapi.model.Petugas;
import com.ternak.sapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetugasRepository extends JpaRepository<Petugas, String> {
    Optional<Petugas> findByNamaPetugas(String namaPetugas);
    Optional<Petugas> findById(String nikPetugas);
}
