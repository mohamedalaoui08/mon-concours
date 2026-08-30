package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Choix;
import com.monconcours.backend.service.ChoixService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@RestController
public class ChoixController {

    private final ChoixService choixService;
    private final EtudiantRepository etudiantRepository;
    private final AbonnementService abonnementService;


    public ChoixController(
            ChoixService choixService,
            EtudiantRepository etudiantRepository,
            AbonnementService abonnementService) {

        this.choixService = choixService;
        this.etudiantRepository = etudiantRepository;
        this.abonnementService = abonnementService;
    }

    @GetMapping("/choix")
    public List<Choix> obtenirTousLesChoix(Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return choixService.obtenirTousLesChoix();
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        if (abonnementService.obtenirAbonnementActif(etudiant).isEmpty()) {
            throw new RuntimeException(
                    "Un abonnement actif est nécessaire pour consulter les choix"
            );
        }

        return choixService.obtenirTousLesChoix();
    }

    @GetMapping("/choix/{id}")
    public Optional<Choix> obtenirChoixParId(
            @PathVariable Integer id,
            Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return choixService.obtenirChoixParId(id);
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        if (abonnementService.obtenirAbonnementActif(etudiant).isEmpty()) {
            throw new RuntimeException(
                    "Un abonnement actif est nécessaire pour consulter les choix"
            );
        }

        return choixService.obtenirChoixParId(id);
    }

    @PostMapping("/choix")
    public Choix ajouterChoix(@RequestBody Choix choix) {
        return choixService.ajouterChoix(choix);
    }

    @PutMapping("/choix/{id}")
    public Choix modifierChoix(@PathVariable Integer id,
                               @RequestBody Choix choix) {
        return choixService.modifierChoix(id, choix);
    }

    @DeleteMapping("/choix/{id}")
    public void supprimerChoix(@PathVariable Integer id) {
        choixService.supprimerChoix(id);
    }
}
