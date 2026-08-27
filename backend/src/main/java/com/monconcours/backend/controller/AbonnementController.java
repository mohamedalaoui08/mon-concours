package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Abonnement;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.dto.SouscrireAbonnementRequest;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.security.core.Authentication;

@RestController
public class AbonnementController {

    private final AbonnementService abonnementService;
    private final EtudiantRepository etudiantRepository;

    public AbonnementController(
            AbonnementService abonnementService,
            EtudiantRepository etudiantRepository) {

        this.abonnementService = abonnementService;
        this.etudiantRepository = etudiantRepository;
    }

    // CREATE
    @PostMapping("/abonnements")
    public Abonnement ajouterAbonnement(@RequestBody Abonnement abonnement) {
        return abonnementService.ajouterAbonnement(abonnement);
    }

    // READ - tous
    @GetMapping("/abonnements")
    public List<Abonnement> obtenirTousLesAbonnements() {
        return abonnementService.obtenirTousLesAbonnements();
    }

    // READ - par id
    @GetMapping("/abonnements/{id}")
    public Optional<Abonnement> obtenirAbonnementParId(@PathVariable Integer id) {
        return abonnementService.obtenirAbonnementParId(id);
    }

    // UPDATE
    @PutMapping("/abonnements/{id}")
    public Abonnement modifierAbonnement(@PathVariable Integer id,
                                         @RequestBody Abonnement abonnement) {
        return abonnementService.modifierAbonnement(id, abonnement);
    }

    // DELETE
    @DeleteMapping("/abonnements/{id}")
    public void supprimerAbonnement(@PathVariable Integer id) {
        abonnementService.supprimerAbonnement(id);
    }

    @PostMapping("/abonnements/souscrire")
    public Abonnement souscrireAbonnement(
            @RequestBody SouscrireAbonnementRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        return abonnementService.souscrireAbonnement(
                etudiant,
                request.getOffreId()
        );
    }

    @GetMapping("/abonnements/mes-abonnements")
    public List<Abonnement> obtenirMesAbonnements(Authentication authentication) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        return abonnementService.obtenirAbonnementsEtudiant(etudiant);
    }

    @GetMapping("/abonnements/mon-abonnement-actif")
    public Optional<Abonnement> obtenirMonAbonnementActif(
            Authentication authentication) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        return abonnementService.obtenirAbonnementActif(etudiant);
    }
}
