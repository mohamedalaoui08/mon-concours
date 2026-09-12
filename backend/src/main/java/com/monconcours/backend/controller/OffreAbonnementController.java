package com.monconcours.backend.controller;

import com.monconcours.backend.entity.OffreAbonnement;
import com.monconcours.backend.service.OffreAbonnementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OffreAbonnementController {

    private final OffreAbonnementService offreAbonnementService;

    public OffreAbonnementController(OffreAbonnementService offreAbonnementService) {
        this.offreAbonnementService = offreAbonnementService;
    }

    @GetMapping("/offres-abonnement")
    public List<OffreAbonnement> obtenirToutesLesOffres() {
        return offreAbonnementService.obtenirToutesLesOffres();
    }

    @GetMapping("/offres-abonnement/{id}")
    public Optional<OffreAbonnement> obtenirOffreParId(@PathVariable Integer id) {
        return offreAbonnementService.obtenirOffreParId(id);
    }

    @PostMapping("/offres-abonnement")
    public OffreAbonnement ajouterOffre(@RequestBody OffreAbonnement offre) {
        return offreAbonnementService.ajouterOffre(offre);
    }

    @PutMapping("/offres-abonnement/{id}")
    public OffreAbonnement modifierOffre(
            @PathVariable Integer id,
            @RequestBody OffreAbonnement offre) {

        return offreAbonnementService.modifierOffre(id, offre);
    }

    @DeleteMapping("/offres-abonnement/{id}")
    public void supprimerOffre(@PathVariable Integer id) {
        offreAbonnementService.supprimerOffre(id);
    }

    @PutMapping("/offres-abonnement/{id}/desactiver")
    public OffreAbonnement desactiverOffre(@PathVariable Integer id) {
        return offreAbonnementService.desactiverOffre(id);
    }

    @PutMapping("/offres-abonnement/{id}/reactiver")
    public OffreAbonnement reactiverOffre(@PathVariable Integer id) {
        return offreAbonnementService.reactiverOffre(id);
    }

    @GetMapping("/offres-abonnement/actives")
    public List<OffreAbonnement> getOffresActives() {
        return offreAbonnementService.getOffresActives();
    }
}
