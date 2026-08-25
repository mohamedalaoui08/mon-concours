package com.monconcours.backend.controller;

import com.monconcours.backend.service.EcoleService;
import org.springframework.web.bind.annotation.RestController;
import com.monconcours.backend.entity.Ecole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
public class EcoleController {

    private final EcoleService ecoleService;

    public EcoleController(EcoleService ecoleService) {
        this.ecoleService = ecoleService;
    }

    @GetMapping("/ecoles")
    public List<Ecole> obtenirToutesLesEcoles() {
        return ecoleService.obtenirToutesLesEcoles();
    }

    @GetMapping("/ecoles/{id}")
    public Optional<Ecole> obtenirEcoleParId(@PathVariable Integer id) {
        return ecoleService.obtenirEcoleParId(id);
    }

    @PostMapping("/ecoles")
    public Ecole ajouterEcole(@RequestBody Ecole ecole) {
        return ecoleService.ajouterEcole(ecole);
    }

    @PutMapping("/ecoles/{id}")
    public Ecole modifierEcole(@PathVariable Integer id,
                               @RequestBody Ecole ecole) {
        return ecoleService.modifierEcole(id, ecole);
    }

    @DeleteMapping("/ecoles/{id}")
    public void supprimerEcole(@PathVariable Integer id) {
        ecoleService.supprimerEcole(id);
    }
}