package com.monconcours.backend.service;

import com.monconcours.backend.entity.Concours;
import com.monconcours.backend.repository.ConcoursRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConcoursService {

    private final ConcoursRepository concoursRepository;

    public ConcoursService(ConcoursRepository concoursRepository) {
        this.concoursRepository = concoursRepository;
    }

    public Concours ajouterConcours(Concours concours) {

        if (concours.isPublicConcours()
                && concoursRepository.countByPublicConcoursTrue() >= 2) {

            throw new RuntimeException(
                    "Il ne peut pas y avoir plus de 2 concours publics"
            );
        }

        return concoursRepository.save(concours);
    }

    public List<Concours> obtenirTousLesConcours() {
        return concoursRepository.findAll();
    }

    public Optional<Concours> obtenirConcoursParId(Integer id) {
        return concoursRepository.findById(id);
    }

    public Concours modifierConcours(Integer id, Concours nouveauConcours) {

        Concours concoursExistant = concoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concours non trouvé"));

        if (nouveauConcours.isPublicConcours()
                && !concoursExistant.isPublicConcours()
                && concoursRepository.countByPublicConcoursTrue() >= 2) {

            throw new RuntimeException(
                    "Il ne peut pas y avoir plus de 2 concours publics"
            );
        }

        concoursExistant.setNom(nouveauConcours.getNom());
        concoursExistant.setDate(nouveauConcours.getDate());
        concoursExistant.setDescription(nouveauConcours.getDescription());
        concoursExistant.setFichierPdf(nouveauConcours.getFichierPdf());
        concoursExistant.setEcole(nouveauConcours.getEcole());
        concoursExistant.setPublicConcours(
                nouveauConcours.isPublicConcours()
        );

        return concoursRepository.save(concoursExistant);
    }

    public void supprimerConcours(Integer id) {
        concoursRepository.deleteById(id);
    }
    public List<Concours> obtenirConcoursPublics() {
        return concoursRepository.findByPublicConcoursTrue();
    }
}
