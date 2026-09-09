package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import com.monconcours.backend.entity.Etudiant;
import java.util.List;
import com.monconcours.backend.entity.QCM;
import java.util.Optional;

public interface ResultatRepository extends JpaRepository<Resultat, Integer> {
    List<Resultat> findByEtudiant(Etudiant etudiant);
    Optional<Resultat> findTopByEtudiantAndQcmOrderByScoreDesc(
            Etudiant etudiant,
            QCM qcm
    );
}
