package com.monconcours.backend.controller;

import com.monconcours.backend.entity.ContenuService;
import com.monconcours.backend.service.ContenuServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contenus-service")
public class ContenuServiceController {

    private final ContenuServiceService contenuServiceService;

    public ContenuServiceController(
            ContenuServiceService contenuServiceService) {
        this.contenuServiceService = contenuServiceService;
    }

    @GetMapping
    public List<ContenuService> obtenirTous() {
        return contenuServiceService.obtenirTous();
    }

    @GetMapping("/{id}")
    public Optional<ContenuService> obtenirParId(
            @PathVariable Integer id) {
        return contenuServiceService.obtenirParId(id);
    }

    @PostMapping
    public ContenuService ajouter(
            @RequestBody ContenuService contenu) {
        return contenuServiceService.ajouter(contenu);
    }

    @PutMapping("/{id}")
    public ContenuService modifier(
            @PathVariable Integer id,
            @RequestBody ContenuService contenu) {
        return contenuServiceService.modifier(id, contenu);
    }

    @DeleteMapping("/{id}")
    public void supprimer(
            @PathVariable Integer id) {
        contenuServiceService.supprimer(id);
    }
}