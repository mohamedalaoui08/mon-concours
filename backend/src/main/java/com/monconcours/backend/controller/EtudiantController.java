package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.service.EtudiantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.security.core.Authentication;

@RestController
public class EtudiantController {

    private final EtudiantService etudiantService;
    private final EtudiantRepository etudiantRepository;

    public EtudiantController(
            EtudiantService etudiantService,
            EtudiantRepository etudiantRepository) {

        this.etudiantService = etudiantService;
        this.etudiantRepository = etudiantRepository;
    }

    // CREATE
    @PostMapping("/etudiants")
    public Etudiant ajouterEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.ajouterEtudiant(etudiant);
    }

    // READ - tous
    @GetMapping("/etudiants")
    public List<Etudiant> obtenirTousLesEtudiants() {
        return etudiantService.obtenirTousLesEtudiants();
    }

    // READ - par id
    @GetMapping("/etudiants/{id}")
    public Optional<Etudiant> obtenirEtudiantParId(@PathVariable Integer id) {
        return etudiantService.obtenirEtudiantParId(id);
    }

    // UPDATE
    @PutMapping("/etudiants/{id}")
    public Etudiant modifierEtudiant(@PathVariable Integer id,
                                     @RequestBody Etudiant etudiant) {
        return etudiantService.modifierEtudiant(id, etudiant);
    }

    // DELETE
    @DeleteMapping("/etudiants/{id}")
    public void supprimerEtudiant(@PathVariable Integer id) {
        etudiantService.supprimerEtudiant(id);
    }

    @GetMapping("/etudiants/mon-profil")
    public Etudiant obtenirMonProfil(Authentication authentication) {

        String email = authentication.getName();

        return etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
    }

    @PutMapping("/etudiants/mon-profil")
    public Etudiant modifierMonProfil(
            Authentication authentication,
            @RequestBody Etudiant nouvellesInfos) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        etudiant.setNom(nouvellesInfos.getNom());
        etudiant.setPrenom(nouvellesInfos.getPrenom());
        etudiant.setDateNaissance(nouvellesInfos.getDateNaissance());
        etudiant.setNiveau(nouvellesInfos.getNiveau());

        return etudiantRepository.save(etudiant);
    }
}