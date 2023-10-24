package com.ternak.sapi.repository;


import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeternakRepository extends JpaRepository<Peternak, String> {
//    Optional<StudyProgram> findByNamaPeternak(String namaPeternak);
    Optional<Peternak> findById(String idPeternak);
}
