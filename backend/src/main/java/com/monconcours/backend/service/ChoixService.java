package com.monconcours.backend.service;

import com.monconcours.backend.entity.Choix;
import com.monconcours.backend.repository.ChoixRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChoixService {

    private final ChoixRepository choixRepository;

    public ChoixService(ChoixRepository choixRepository) {
        this.choixRepository = choixRepository;
    }

    public Choix ajouterChoix(Choix choix) {
        return choixRepository.save(choix);
    }

    public List<Choix> obtenirTousLesChoix() {
        return choixRepository.findAll();
    }

    public Optional<Choix> obtenirChoixParId(Integer id) {
        return choixRepository.findById(id);
    }

    public Choix modifierChoix(Integer id, Choix nouveauChoix) {

        Choix choixExistant = choixRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Choix non trouvé"));

        choixExistant.setTexte(nouveauChoix.getTexte());
        choixExistant.setEstCorrecte(nouveauChoix.isEstCorrecte());
        choixExistant.setQuestion(nouveauChoix.getQuestion());

        return choixRepository.save(choixExistant);
    }

    public void supprimerChoix(Integer id) {
        choixRepository.deleteById(id);
    }
}
