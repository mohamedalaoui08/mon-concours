package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Actualite;
import com.monconcours.backend.service.ActualiteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ActualiteController {

    private final ActualiteService actualiteService;

    public ActualiteController(ActualiteService actualiteService) {
        this.actualiteService = actualiteService;
    }

    // CREATE
    @PostMapping("/actualites")
    public Actualite ajouterActualite(@RequestBody Actualite actualite) {
        return actualiteService.ajouterActualite(actualite);
    }

    // READ - toutes
    @GetMapping("/actualites")
    public List<Actualite> obtenirToutesLesActualites() {
        return actualiteService.obtenirToutesLesActualites();
    }

    // READ - par id
    @GetMapping("/actualites/{id}")
    public Optional<Actualite> obtenirActualiteParId(@PathVariable Integer id) {
        return actualiteService.obtenirActualiteParId(id);
    }

    // UPDATE
    @PutMapping("/actualites/{id}")
    public Actualite modifierActualite(@PathVariable Integer id,
                                       @RequestBody Actualite actualite) {
        return actualiteService.modifierActualite(id, actualite);
    }

    // DELETE
    @DeleteMapping("/actualites/{id}")
    public void supprimerActualite(@PathVariable Integer id) {
        actualiteService.supprimerActualite(id);
    }
}