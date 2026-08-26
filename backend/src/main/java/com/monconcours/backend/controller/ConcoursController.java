package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Concours;
import com.monconcours.backend.service.ConcoursService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConcoursController {

    private final ConcoursService concoursService;

    public ConcoursController(ConcoursService concoursService) {
        this.concoursService = concoursService;
    }

    @GetMapping("/concours")
    public List<Concours> obtenirTousLesConcours() {
        return concoursService.obtenirTousLesConcours();
    }

    @GetMapping("/concours/{id}")
    public Optional<Concours> obtenirConcoursParId(@PathVariable Integer id) {
        return concoursService.obtenirConcoursParId(id);
    }

    @PostMapping("/concours")
    public Concours ajouterConcours(@RequestBody Concours concours) {
        return concoursService.ajouterConcours(concours);
    }

    @PutMapping("/concours/{id}")
    public Concours modifierConcours(@PathVariable Integer id,
                                     @RequestBody Concours concours) {
        return concoursService.modifierConcours(id, concours);
    }

    @DeleteMapping("/concours/{id}")
    public void supprimerConcours(@PathVariable Integer id) {
        concoursService.supprimerConcours(id);
    }

    @GetMapping("/concours/public")
    public List<Concours> obtenirConcoursPublics() {
        return concoursService.obtenirConcoursPublics();
    }
}
