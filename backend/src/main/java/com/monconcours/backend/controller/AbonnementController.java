package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Abonnement;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AbonnementController {

    private final AbonnementService abonnementService;

    public AbonnementController(AbonnementService abonnementService) {
        this.abonnementService = abonnementService;
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
}
