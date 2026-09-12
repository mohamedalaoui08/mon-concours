package com.monconcours.backend.repository;

import com.monconcours.backend.entity.OffreAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffreAbonnementRepository
        extends JpaRepository<OffreAbonnement, Integer> {
    List<OffreAbonnement> findByActiveTrue();
}