package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Resultat;
import com.monconcours.backend.service.ResultatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ResultatController {

    private final ResultatService resultatService;

    public ResultatController(ResultatService resultatService) {
        this.resultatService = resultatService;
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
}
