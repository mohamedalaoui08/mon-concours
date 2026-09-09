package com.monconcours.backend.service;

import com.monconcours.backend.entity.Abonnement;
import com.monconcours.backend.repository.AbonnementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.entity.OffreAbonnement;
import com.monconcours.backend.repository.OffreAbonnementRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;
    private final OffreAbonnementRepository offreAbonnementRepository;

    public AbonnementService(AbonnementRepository abonnementRepository, OffreAbonnementRepository offreAbonnementRepository) {
        this.abonnementRepository = abonnementRepository;
        this.offreAbonnementRepository = offreAbonnementRepository;
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

        abonnementExistant.setOffreAbonnement(
                nouvelAbonnement.getOffreAbonnement()
        );
        abonnementExistant.setDateDebut(nouvelAbonnement.getDateDebut());
        abonnementExistant.setDateFin(nouvelAbonnement.getDateFin());
        abonnementExistant.setStatut(nouvelAbonnement.getStatut());

        return abonnementRepository.save(abonnementExistant);
    }

    // DELETE
    public void supprimerAbonnement(Integer id) {
        abonnementRepository.deleteById(id);
    }

    public Abonnement souscrireAbonnement(
            Etudiant etudiant,
            Integer offreId) {

        Optional<Abonnement> abonnementActif = obtenirAbonnementActif(etudiant);

        if (abonnementActif.isPresent()) {
            throw new RuntimeException("Vous avez déjà un abonnement actif");
        }

        OffreAbonnement offre = offreAbonnementRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        Abonnement abonnement = new Abonnement();

        abonnement.setEtudiant(etudiant);
        abonnement.setOffreAbonnement(offre);
        abonnement.setDateDebut(LocalDate.now());
        abonnement.setDateFin(LocalDate.now().plusDays(offre.getDureeJours()));
        abonnement.setStatut("ACTIF");

        return abonnementRepository.save(abonnement);
    }

    public List<Abonnement> obtenirAbonnementsEtudiant(Etudiant etudiant) {
        return abonnementRepository.findByEtudiant(etudiant);
    }

    public Optional<Abonnement> obtenirAbonnementActif(Etudiant etudiant) {

        Optional<Abonnement> abonnementOptional =
                abonnementRepository.findFirstByEtudiantAndStatutOrderByDateFinDesc(
                        etudiant,
                        "ACTIF"
                );

        if (abonnementOptional.isEmpty()) {
            return Optional.empty();
        }

        Abonnement abonnement = abonnementOptional.get();

        if (abonnement.getDateFin().isBefore(LocalDate.now())) {
            abonnement.setStatut("EXPIRE");
            abonnementRepository.save(abonnement);

            return Optional.empty();
        }

        return Optional.of(abonnement);
    }

    public Abonnement resilierAbonnement(Etudiant etudiant) {

        Abonnement abonnement = obtenirAbonnementActif(etudiant)
                .orElseThrow(() ->
                        new RuntimeException("Aucun abonnement actif"));

        abonnement.setStatut("ANNULE");

        return abonnementRepository.save(abonnement);
    }

    public Abonnement changerAbonnement(
            Etudiant etudiant,
            Integer offreId) {

        // Récupérer l'abonnement actuellement actif
        Abonnement abonnementActuel = obtenirAbonnementActif(etudiant)
                .orElseThrow(() ->
                        new RuntimeException("Aucun abonnement actif"));

        // Récupérer la nouvelle offre choisie
        OffreAbonnement nouvelleOffre =
                offreAbonnementRepository.findById(offreId)
                        .orElseThrow(() ->
                                new RuntimeException("Offre non trouvée"));

        // Empêcher de choisir la même offre
        if (abonnementActuel.getOffreAbonnement().getId()
                .equals(nouvelleOffre.getId())) {

            throw new RuntimeException(
                    "Vous avez déjà cet abonnement"
            );
        }

        // Annuler l'ancien abonnement
        abonnementActuel.setStatut("ANNULE");
        abonnementRepository.save(abonnementActuel);

        // Créer le nouvel abonnement
        Abonnement nouvelAbonnement = new Abonnement();

        nouvelAbonnement.setEtudiant(etudiant);
        nouvelAbonnement.setOffreAbonnement(nouvelleOffre);
        nouvelAbonnement.setDateDebut(LocalDate.now());
        nouvelAbonnement.setDateFin(
                LocalDate.now().plusDays(
                        nouvelleOffre.getDureeJours()
                )
        );
        nouvelAbonnement.setStatut("ACTIF");

        return abonnementRepository.save(nouvelAbonnement);
    }
}
