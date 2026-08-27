package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import com.monconcours.backend.entity.Etudiant;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    List<Abonnement> findByEtudiant(Etudiant etudiant);
    Optional<Abonnement> findFirstByEtudiantAndStatutOrderByDateFinDesc(
            Etudiant etudiant,
            String statut
    );
}
