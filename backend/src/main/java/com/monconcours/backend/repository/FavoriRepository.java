package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Favori;
import org.springframework.data.jpa.repository.JpaRepository;
import com.monconcours.backend.entity.Etudiant;
import java.util.List;

public interface FavoriRepository extends JpaRepository<Favori, Integer> {
    List<Favori> findByEtudiant(Etudiant etudiant);
    boolean existsByEtudiantAndConcours_Id(Etudiant etudiant, Integer concoursId);

    boolean existsByEtudiantAndQcm_Id(Etudiant etudiant, Integer qcmId);

    boolean existsByEtudiantAndExercice_Id(Etudiant etudiant, Integer exerciceId);

    boolean existsByEtudiantAndFormation_Id(Etudiant etudiant, Integer formationId);

    boolean existsByEtudiantAndActualite_Id(Etudiant etudiant, Integer actualiteId);
}
