package com.monconcours.backend.service;

import com.monconcours.backend.entity.OffreAbonnement;
import com.monconcours.backend.repository.OffreAbonnementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffreAbonnementService {

    private final OffreAbonnementRepository offreAbonnementRepository;

    public OffreAbonnementService(OffreAbonnementRepository offreAbonnementRepository) {
        this.offreAbonnementRepository = offreAbonnementRepository;
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

        return offreAbonnementRepository.save(offreExistante);
    }

    // DELETE
    public void supprimerOffre(Integer id) {
        offreAbonnementRepository.deleteById(id);
    }
}