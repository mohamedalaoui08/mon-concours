package com.monconcours.backend.repository;

import com.monconcours.backend.entity.DemandeInscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeInscriptionRepository extends JpaRepository<DemandeInscription, Integer> {
}