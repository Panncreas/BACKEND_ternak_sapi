package com.ternak.sapi.repository;

import com.ternak.sapi.model.Vaksin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaksinRepository extends JpaRepository<Vaksin, String> {
    Optional<Vaksin> findById(String idVaksin);
}