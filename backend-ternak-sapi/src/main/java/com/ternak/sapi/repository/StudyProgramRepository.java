package com.ternak.sapi.repository;


import com.ternak.sapi.model.StudyProgram;
import com.ternak.sapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyProgramRepository extends JpaRepository<StudyProgram, String> {
    Optional<StudyProgram> findByName(String username);
    Optional<StudyProgram> findById(String idPeternak);
}
