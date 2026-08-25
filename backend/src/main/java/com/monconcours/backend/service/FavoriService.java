package com.monconcours.backend.service;

import com.monconcours.backend.entity.Favori;
import com.monconcours.backend.repository.FavoriRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriService {

    private final FavoriRepository favoriRepository;

    public FavoriService(FavoriRepository favoriRepository) {
        this.favoriRepository = favoriRepository;
    }

    // CREATE
    public Favori ajouterFavori(Favori favori) {
        return favoriRepository.save(favori);
    }

    // READ - tous
    public List<Favori> obtenirTousLesFavoris() {
        return favoriRepository.findAll();
    }

    // READ - par id
    public Optional<Favori> obtenirFavoriParId(Integer id) {
        return favoriRepository.findById(id);
    }

    // UPDATE
    public Favori modifierFavori(Integer id, Favori nouveauFavori) {

        Favori favoriExistant = favoriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favori non trouvé"));

        favoriExistant.setDateAjout(nouveauFavori.getDateAjout());
        favoriExistant.setEtudiant(nouveauFavori.getEtudiant());
        favoriExistant.setConcours(nouveauFavori.getConcours());
        favoriExistant.setExercice(nouveauFavori.getExercice());
        favoriExistant.setFormation(nouveauFavori.getFormation());
        favoriExistant.setQcm(nouveauFavori.getQcm());
        favoriExistant.setActualite(nouveauFavori.getActualite());

        return favoriRepository.save(favoriExistant);
    }

    // DELETE
    public void supprimerFavori(Integer id) {
        favoriRepository.deleteById(id);
    }
}
