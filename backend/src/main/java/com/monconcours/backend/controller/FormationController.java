package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Formation;
import com.monconcours.backend.service.FormationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@RestController
public class FormationController {

    private final FormationService formationService;
    private final EtudiantRepository etudiantRepository;
    private final AbonnementService abonnementService;

    public FormationController(
            FormationService formationService,
            EtudiantRepository etudiantRepository,
            AbonnementService abonnementService) {

        this.formationService = formationService;
        this.etudiantRepository = etudiantRepository;
        this.abonnementService = abonnementService;
    }

    // CREATE
    @PostMapping("/formations")
    public Formation ajouterFormation(@RequestBody Formation formation) {
        return formationService.ajouterFormation(formation);
    }

    // READ - toutes
    @GetMapping("/formations")
    public List<Formation> obtenirToutesLesFormations(
            Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return formationService.obtenirToutesLesFormations();
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        if (abonnementService.obtenirAbonnementActif(etudiant).isEmpty()) {
            throw new RuntimeException(
                    "Un abonnement actif est nécessaire pour consulter les formations"
            );
        }

        return formationService.obtenirToutesLesFormations();
    }
    // READ - par id
    @GetMapping("/formations/{id}")
    public Optional<Formation> obtenirFormationParId(
            @PathVariable Integer id,
            Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return formationService.obtenirFormationParId(id);
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        if (abonnementService.obtenirAbonnementActif(etudiant).isEmpty()) {
            throw new RuntimeException(
                    "Un abonnement actif est nécessaire pour consulter les formations"
            );
        }

        return formationService.obtenirFormationParId(id);
    }

    // UPDATE
    @PutMapping("/formations/{id}")
    public Formation modifierFormation(@PathVariable Integer id,
                                       @RequestBody Formation formation) {
        return formationService.modifierFormation(id, formation);
    }

    // DELETE
    @DeleteMapping("/formations/{id}")
    public void supprimerFormation(@PathVariable Integer id) {
        formationService.supprimerFormation(id);
    }
}