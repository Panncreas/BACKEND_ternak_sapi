package com.ternak.sapi.repository;

import com.ternak.sapi.model.Inseminasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InseminasiRepository extends JpaRepository<Inseminasi, String> {
    Optional<Inseminasi> findById(String idInseminasi);
}
