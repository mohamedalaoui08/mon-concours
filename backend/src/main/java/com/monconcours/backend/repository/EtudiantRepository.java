package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {
    Optional<Etudiant> findByEmail(String email);

}
