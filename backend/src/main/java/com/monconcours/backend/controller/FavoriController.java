package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Favori;
import com.monconcours.backend.service.FavoriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.security.core.Authentication;
import com.monconcours.backend.dto.AjouterFavoriRequest;
import com.monconcours.backend.dto.FavoriResponse;
@RestController
public class FavoriController {

    private final FavoriService favoriService;
    private final EtudiantRepository etudiantRepository;

    public FavoriController(
            FavoriService favoriService,
            EtudiantRepository etudiantRepository) {

        this.favoriService = favoriService;
        this.etudiantRepository = etudiantRepository;
    }

    // CREATE
    @PostMapping("/favoris")
    public Favori ajouterFavori(
            @RequestBody AjouterFavoriRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        return favoriService.ajouterFavoriSecurise(etudiant, request);
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
    public void supprimerFavori(@PathVariable Integer id, Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (estAdmin) {
            favoriService.supprimerFavori(id);
            return;
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        favoriService.supprimerFavoriEtudiant(id, etudiant);
    }

    @GetMapping("/favoris/mes-favoris")
    public List<FavoriResponse> obtenirMesFavoris(Authentication authentication) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        return favoriService.obtenirFavorisEtudiantResponse(etudiant);
    }
}
