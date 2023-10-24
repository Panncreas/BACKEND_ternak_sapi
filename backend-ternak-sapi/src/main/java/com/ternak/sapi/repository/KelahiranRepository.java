package com.ternak.sapi.repository;

import com.ternak.sapi.model.Kelahiran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KelahiranRepository extends JpaRepository<Kelahiran, String> {
//    Optional<Kelahiran> findByName(String idKejadian);
    Optional<Kelahiran> findById(String idKejadian);
}
