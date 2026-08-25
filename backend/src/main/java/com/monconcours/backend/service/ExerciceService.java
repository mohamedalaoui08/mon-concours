package com.monconcours.backend.service;

import com.monconcours.backend.entity.Exercice;
import com.monconcours.backend.repository.ExerciceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciceService {

    private final ExerciceRepository exerciceRepository;

    public ExerciceService(ExerciceRepository exerciceRepository) {
        this.exerciceRepository = exerciceRepository;
    }

    public Exercice ajouterExercice(Exercice exercice) {
        return exerciceRepository.save(exercice);
    }

    public List<Exercice> obtenirTousLesExercices() {
        return exerciceRepository.findAll();
    }

    public Optional<Exercice> obtenirExerciceParId(Integer id) {
        return exerciceRepository.findById(id);
    }

    public Exercice modifierExercice(Integer id, Exercice nouvelExercice) {

        Exercice exerciceExistant = exerciceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercice non trouvé"));

        exerciceExistant.setTitre(nouvelExercice.getTitre());
        exerciceExistant.setEnonce(nouvelExercice.getEnonce());
        exerciceExistant.setMatiere(nouvelExercice.getMatiere());

        return exerciceRepository.save(exerciceExistant);
    }

    public void supprimerExercice(Integer id) {
        exerciceRepository.deleteById(id);
    }
}
