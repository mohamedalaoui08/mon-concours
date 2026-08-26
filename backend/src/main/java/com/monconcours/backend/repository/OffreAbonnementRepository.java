package com.monconcours.backend.repository;

import com.monconcours.backend.entity.OffreAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreAbonnementRepository
        extends JpaRepository<OffreAbonnement, Integer> {
}