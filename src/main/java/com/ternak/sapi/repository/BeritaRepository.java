package com.ternak.sapi.repository;

import com.ternak.sapi.model.Berita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeritaRepository extends JpaRepository<Berita, Long> {
    Optional<Berita> findById(Long id);
}