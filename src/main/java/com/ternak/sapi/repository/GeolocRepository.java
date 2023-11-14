package com.ternak.sapi.repository;

import com.ternak.sapi.model.Geoloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeolocRepository extends JpaRepository<Geoloc, String> {
    Optional<Geoloc> findById(String id);
}
