package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Formation;
import com.monconcours.backend.service.FormationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FormationController {

    private final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    // CREATE
    @PostMapping("/formations")
    public Formation ajouterFormation(@RequestBody Formation formation) {
        return formationService.ajouterFormation(formation);
    }

    // READ - toutes
    @GetMapping("/formations")
    public List<Formation> obtenirToutesLesFormations() {
        return formationService.obtenirToutesLesFormations();
    }

    // READ - par id
    @GetMapping("/formations/{id}")
    public Optional<Formation> obtenirFormationParId(@PathVariable Integer id) {
        return formationService.obtenirFormationParId(id);
    }

    // UPDATE
    @PutMapping("/formations/{id}")
    public Formation modifierFormation(@PathVariable Integer id,
                                       @RequestBody Formation formation) {
        return formationService.modifierFormation(id, formation);
    }

    // DELETE
    @DeleteMapping("/formations/{id}")
    public void supprimerFormation(@PathVariable Integer id) {
        formationService.supprimerFormation(id);
    }
}