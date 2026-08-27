package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import com.monconcours.backend.entity.Etudiant;
import java.util.List;

public interface ResultatRepository extends JpaRepository<Resultat, Integer> {
    List<Resultat> findByEtudiant(Etudiant etudiant);
}
