package com.monconcours.backend.service;

import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.entity.Resultat;
import com.monconcours.backend.repository.ResultatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultatService {

    private final ResultatRepository resultatRepository;

    public ResultatService(ResultatRepository resultatRepository) {
        this.resultatRepository = resultatRepository;
    }

    public Resultat ajouterResultat(Resultat resultat) {
        return resultatRepository.save(resultat);
    }

    public List<Resultat> obtenirTousLesResultats() {
        return resultatRepository.findAll();
    }

    public Optional<Resultat> obtenirResultatParId(Integer id) {
        return resultatRepository.findById(id);
    }

    public Resultat modifierResultat(Integer id, Resultat nouveauResultat) {

        Resultat resultatExistant = resultatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resultat non trouvé"));

        resultatExistant.setScore(nouveauResultat.getScore());
        resultatExistant.setDatePassage(nouveauResultat.getDatePassage());
        resultatExistant.setQcm(nouveauResultat.getQcm());
        resultatExistant.setEtudiant(nouveauResultat.getEtudiant());

        return resultatRepository.save(resultatExistant);
    }

    public void supprimerResultat(Integer id) {
        resultatRepository.deleteById(id);
    }

    public List<Resultat> obtenirResultatsEtudiant(Etudiant etudiant) {
        return resultatRepository.findByEtudiant(etudiant);
    }
}
