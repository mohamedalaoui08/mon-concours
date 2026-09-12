package com.monconcours.backend.service;

import com.monconcours.backend.entity.OffreAbonnement;
import com.monconcours.backend.repository.AbonnementRepository;
import com.monconcours.backend.repository.OffreAbonnementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OffreAbonnementService {

    private final OffreAbonnementRepository offreAbonnementRepository;
    private final AbonnementRepository abonnementRepository;

    public OffreAbonnementService(
            OffreAbonnementRepository offreAbonnementRepository,
            AbonnementRepository abonnementRepository) {

        this.offreAbonnementRepository = offreAbonnementRepository;
        this.abonnementRepository = abonnementRepository;
    }

    // CREATE
    public OffreAbonnement ajouterOffre(OffreAbonnement offre) {
        return offreAbonnementRepository.save(offre);
    }

    // READ - toutes
    public List<OffreAbonnement> obtenirToutesLesOffres() {
        return offreAbonnementRepository.findAll();
    }

    // READ - par id
    public Optional<OffreAbonnement> obtenirOffreParId(Integer id) {
        return offreAbonnementRepository.findById(id);
    }

    // UPDATE
    public OffreAbonnement modifierOffre(Integer id, OffreAbonnement nouvelleOffre) {

        OffreAbonnement offreExistante = offreAbonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        offreExistante.setNom(nouvelleOffre.getNom());
        offreExistante.setDescription(nouvelleOffre.getDescription());
        offreExistante.setPrix(nouvelleOffre.getPrix());
        offreExistante.setDureeJours(nouvelleOffre.getDureeJours());
        offreExistante.setAccesConcours(
                nouvelleOffre.isAccesConcours()
        );

        offreExistante.setAccesQcm(
                nouvelleOffre.isAccesQcm()
        );

        offreExistante.setAccesExercices(
                nouvelleOffre.isAccesExercices()
        );

        offreExistante.setAccesFormations(
                nouvelleOffre.isAccesFormations()
        );

        return offreAbonnementRepository.save(offreExistante);
    }

    // DELETE
    public void supprimerOffre(Integer id) {

        if (abonnementRepository.existsByOffreAbonnementId(id)) {
            throw new RuntimeException(
                    "Impossible de supprimer cette offre car elle est utilisée par un abonnement."
            );
        }

        offreAbonnementRepository.deleteById(id);
    }

    public OffreAbonnement desactiverOffre(Integer id) {

        OffreAbonnement offre = offreAbonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        offre.setActive(false);

        return offreAbonnementRepository.save(offre);
    }

    public OffreAbonnement reactiverOffre(Integer id) {

        OffreAbonnement offre = offreAbonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        offre.setActive(true);

        return offreAbonnementRepository.save(offre);
    }

    public List<OffreAbonnement> getOffresActives() {
        return offreAbonnementRepository.findByActiveTrue();
    }

}