package com.ternak.sapi.repository;


import com.ternak.sapi.model.Kandang;
import com.ternak.sapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KandangRepository extends JpaRepository<Kandang, String> {
    Optional<Kandang> findById(String idKandang);
}
