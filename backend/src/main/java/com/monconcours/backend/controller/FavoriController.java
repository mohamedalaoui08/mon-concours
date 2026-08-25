package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Favori;
import com.monconcours.backend.service.FavoriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FavoriController {

    private final FavoriService favoriService;

    public FavoriController(FavoriService favoriService) {
        this.favoriService = favoriService;
    }

    // CREATE
    @PostMapping("/favoris")
    public Favori ajouterFavori(@RequestBody Favori favori) {
        return favoriService.ajouterFavori(favori);
    }

    // READ - tous
    @GetMapping("/favoris")
    public List<Favori> obtenirTousLesFavoris() {
        return favoriService.obtenirTousLesFavoris();
    }

    // READ - par id
    @GetMapping("/favoris/{id}")
    public Optional<Favori> obtenirFavoriParId(@PathVariable Integer id) {
        return favoriService.obtenirFavoriParId(id);
    }

    // UPDATE
    @PutMapping("/favoris/{id}")
    public Favori modifierFavori(@PathVariable Integer id,
                                 @RequestBody Favori favori) {
        return favoriService.modifierFavori(id, favori);
    }

    // DELETE
    @DeleteMapping("/favoris/{id}")
    public void supprimerFavori(@PathVariable Integer id) {
        favoriService.supprimerFavori(id);
    }
}
