package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Resultat;
import com.monconcours.backend.service.ResultatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.security.core.Authentication;

@RestController
public class ResultatController {

    private final ResultatService resultatService;
    private final EtudiantRepository etudiantRepository;

    public ResultatController(
            ResultatService resultatService,
            EtudiantRepository etudiantRepository) {

        this.resultatService = resultatService;
        this.etudiantRepository = etudiantRepository;
    }

    @GetMapping("/resultats")
    public List<Resultat> obtenirTousLesResultats() {
        return resultatService.obtenirTousLesResultats();
    }

    @GetMapping("/resultats/{id}")
    public Optional<Resultat> obtenirResultatParId(@PathVariable Integer id) {
        return resultatService.obtenirResultatParId(id);
    }

    @PostMapping("/resultats")
    public Resultat ajouterResultat(@RequestBody Resultat resultat) {
        return resultatService.ajouterResultat(resultat);
    }

    @PutMapping("/resultats/{id}")
    public Resultat modifierResultat(@PathVariable Integer id,
                                     @RequestBody Resultat resultat) {
        return resultatService.modifierResultat(id, resultat);
    }

    @DeleteMapping("/resultats/{id}")
    public void supprimerResultat(@PathVariable Integer id) {
        resultatService.supprimerResultat(id);
    }

    @GetMapping("/resultats/mes-resultats")
    public List<Resultat> obtenirMesResultats(Authentication authentication) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        return resultatService.obtenirResultatsEtudiant(etudiant);
    }
}
