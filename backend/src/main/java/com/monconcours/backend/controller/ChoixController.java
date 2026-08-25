package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Choix;
import com.monconcours.backend.service.ChoixService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ChoixController {

    private final ChoixService choixService;

    public ChoixController(ChoixService choixService) {
        this.choixService = choixService;
    }

    @GetMapping("/choix")
    public List<Choix> obtenirTousLesChoix() {
        return choixService.obtenirTousLesChoix();
    }

    @GetMapping("/choix/{id}")
    public Optional<Choix> obtenirChoixParId(@PathVariable Integer id) {
        return choixService.obtenirChoixParId(id);
    }

    @PostMapping("/choix")
    public Choix ajouterChoix(@RequestBody Choix choix) {
        return choixService.ajouterChoix(choix);
    }

    @PutMapping("/choix/{id}")
    public Choix modifierChoix(@PathVariable Integer id,
                               @RequestBody Choix choix) {
        return choixService.modifierChoix(id, choix);
    }

    @DeleteMapping("/choix/{id}")
    public void supprimerChoix(@PathVariable Integer id) {
        choixService.supprimerChoix(id);
    }
}
