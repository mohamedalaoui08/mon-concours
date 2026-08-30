package com.monconcours.backend.controller;

import com.monconcours.backend.entity.DemandeInscription;
import com.monconcours.backend.service.DemandeInscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

@RestController
public class DemandeInscriptionController {

    private final DemandeInscriptionService demandeInscriptionService;

    public DemandeInscriptionController(DemandeInscriptionService demandeInscriptionService) {
        this.demandeInscriptionService = demandeInscriptionService;
    }

    // CREATE
    @PostMapping("/demandes-inscription")
    public DemandeInscription ajouterDemande(@Valid @RequestBody DemandeInscription demande) {
        return demandeInscriptionService.ajouterDemande(demande);
    }

    // READ - toutes
    @GetMapping("/demandes-inscription")
    public List<DemandeInscription> obtenirToutesLesDemandes() {
        return demandeInscriptionService.obtenirToutesLesDemandes();
    }

    // READ - par id
    @GetMapping("/demandes-inscription/{id}")
    public Optional<DemandeInscription> obtenirDemandeParId(@PathVariable Integer id) {
        return demandeInscriptionService.obtenirDemandeParId(id);
    }

    // DELETE
    @DeleteMapping("/demandes-inscription/{id}")
    public void supprimerDemande(@PathVariable Integer id) {
        demandeInscriptionService.supprimerDemande(id);
    }
    // ACCEPTE DE DEMANDE
    @PutMapping("/demandes-inscription/{id}/accepter")
    public DemandeInscription accepterDemande(@PathVariable Integer id) {
        return demandeInscriptionService.accepterDemande(id);
    }
}