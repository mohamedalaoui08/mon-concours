package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Favori;
import org.springframework.data.jpa.repository.JpaRepository;
import com.monconcours.backend.entity.Etudiant;
import java.util.List;

public interface FavoriRepository extends JpaRepository<Favori, Integer> {
    List<Favori> findByEtudiant(Etudiant etudiant);
}
