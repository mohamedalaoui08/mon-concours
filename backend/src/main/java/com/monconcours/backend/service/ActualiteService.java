package com.monconcours.backend.service;

import com.monconcours.backend.entity.Actualite;
import com.monconcours.backend.repository.ActualiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActualiteService {

    private final ActualiteRepository actualiteRepository;

    public ActualiteService(ActualiteRepository actualiteRepository) {
        this.actualiteRepository = actualiteRepository;
    }

    // CREATE
    public Actualite ajouterActualite(Actualite actualite) {
        return actualiteRepository.save(actualite);
    }

    // READ - toutes
    public List<Actualite> obtenirToutesLesActualites() {
        return actualiteRepository.findAll();
    }

    // READ - par id
    public Optional<Actualite> obtenirActualiteParId(Integer id) {
        return actualiteRepository.findById(id);
    }

    // UPDATE
    public Actualite modifierActualite(Integer id, Actualite nouvelleActualite) {

        Actualite actualiteExistante = actualiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actualite non trouvée"));

        actualiteExistante.setTitre(nouvelleActualite.getTitre());
        actualiteExistante.setContenu(nouvelleActualite.getContenu());
        actualiteExistante.setDatePublication(nouvelleActualite.getDatePublication());
        actualiteExistante.setEcole(nouvelleActualite.getEcole());

        return actualiteRepository.save(actualiteExistante);
    }

    // DELETE
    public void supprimerActualite(Integer id) {
        actualiteRepository.deleteById(id);
    }
}
