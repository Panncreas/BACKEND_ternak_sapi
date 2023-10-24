package com.ternak.sapi.repository;

import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HewanRepository extends JpaRepository<Hewan, String> {
//    Optional<Department> findByName(String username);
    Optional<Hewan> findById(String kodeEartagNasional);
}

