package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Exercice;
import com.monconcours.backend.service.ExerciceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.repository.EtudiantRepository;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@RestController
public class ExerciceController {

    private final ExerciceService exerciceService;
    private final EtudiantRepository etudiantRepository;
    private final AbonnementService abonnementService;

    public ExerciceController(
            ExerciceService exerciceService,
            EtudiantRepository etudiantRepository,
            AbonnementService abonnementService) {

        this.exerciceService = exerciceService;
        this.etudiantRepository = etudiantRepository;
        this.abonnementService = abonnementService;
    }

    @GetMapping("/exercices")
    public List<Exercice> obtenirTousLesExercices(Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return exerciceService.obtenirTousLesExercices();
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        var abonnement = abonnementService.obtenirAbonnementActif(etudiant)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Un abonnement actif est nécessaire pour consulter les exercices"
                        )
                );

        if (!abonnement.getOffreAbonnement().isAccesExercices()) {
            throw new RuntimeException(
                    "Votre abonnement ne permet pas l'accès aux exercices"
            );
        }

        return exerciceService.obtenirTousLesExercices();
    }

    @GetMapping("/exercices/{id}")
    public Optional<Exercice> obtenirExerciceParId(
            @PathVariable Integer id,
            Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return exerciceService.obtenirExerciceParId(id);
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        var abonnement = abonnementService.obtenirAbonnementActif(etudiant)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Un abonnement actif est nécessaire pour consulter les exercices"
                        )
                );

        if (!abonnement.getOffreAbonnement().isAccesExercices()) {
            throw new RuntimeException(
                    "Votre abonnement ne permet pas l'accès aux exercices"
            );
        }

        return exerciceService.obtenirExerciceParId(id);
    }

    @PostMapping("/exercices")
    public Exercice ajouterExercice(@RequestBody Exercice exercice) {
        return exerciceService.ajouterExercice(exercice);
    }

    @PutMapping("/exercices/{id}")
    public Exercice modifierExercice(@PathVariable Integer id,
                                     @RequestBody Exercice exercice) {
        return exerciceService.modifierExercice(id, exercice);
    }

    @DeleteMapping("/exercices/{id}")
    public void supprimerExercice(@PathVariable Integer id) {
        exerciceService.supprimerExercice(id);
    }
}