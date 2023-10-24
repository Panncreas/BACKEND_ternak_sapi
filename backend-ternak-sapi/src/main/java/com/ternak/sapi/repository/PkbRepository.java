package com.ternak.sapi.repository;

import com.ternak.sapi.model.Pkb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PkbRepository extends JpaRepository<Pkb, String> {
    Optional<Pkb> findById(String idKejadian);
}