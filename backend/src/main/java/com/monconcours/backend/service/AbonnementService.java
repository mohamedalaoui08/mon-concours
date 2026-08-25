package com.monconcours.backend.service;

import com.monconcours.backend.entity.Abonnement;
import com.monconcours.backend.repository.AbonnementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;

    public AbonnementService(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

    // CREATE
    public Abonnement ajouterAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    // READ - tous
    public List<Abonnement> obtenirTousLesAbonnements() {
        return abonnementRepository.findAll();
    }

    // READ - par id
    public Optional<Abonnement> obtenirAbonnementParId(Integer id) {
        return abonnementRepository.findById(id);
    }

    // UPDATE
    public Abonnement modifierAbonnement(Integer id, Abonnement nouvelAbonnement) {

        Abonnement abonnementExistant = abonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé"));

        abonnementExistant.setType(nouvelAbonnement.getType());
        abonnementExistant.setDateDebut(nouvelAbonnement.getDateDebut());
        abonnementExistant.setDateFin(nouvelAbonnement.getDateFin());
        abonnementExistant.setStatut(nouvelAbonnement.getStatut());
        abonnementExistant.setEtudiant(nouvelAbonnement.getEtudiant());

        return abonnementRepository.save(abonnementExistant);
    }

    // DELETE
    public void supprimerAbonnement(Integer id) {
        abonnementRepository.deleteById(id);
    }
}
