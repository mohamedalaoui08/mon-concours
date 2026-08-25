package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.service.EtudiantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
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
}