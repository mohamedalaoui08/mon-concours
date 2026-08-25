package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Exercice;
import com.monconcours.backend.service.ExerciceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExerciceController {

    private final ExerciceService exerciceService;

    public ExerciceController(ExerciceService exerciceService) {
        this.exerciceService = exerciceService;
    }

    @GetMapping("/exercices")
    public List<Exercice> obtenirTousLesExercices() {
        return exerciceService.obtenirTousLesExercices();
    }

    @GetMapping("/exercices/{id}")
    public Optional<Exercice> obtenirExerciceParId(@PathVariable Integer id) {
        return exerciceService.obtenirExerciceParId(id);
    }

    @PostMapping("/exercices")
    public Exercice ajouterExercice(@RequestBody Exercice exercice) {
        return exerciceService.ajouterExercice(exercice);
    }

    @PutMapping("/exercices/{id}")
    public Exercice modifierExercice(@PathVariable Integer id,
                                     @RequestBody Exercice exercice) {
        return exerciceService.modifierExercice(id, exercice);
    }

    @DeleteMapping("/exercices/{id}")
    public void supprimerExercice(@PathVariable Integer id) {
        exerciceService.supprimerExercice(id);
    }
}