package com.monconcours.backend.service;

import com.monconcours.backend.entity.Favori;
import com.monconcours.backend.repository.FavoriRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.QCMRepository;
import com.monconcours.backend.repository.ConcoursRepository;
import com.monconcours.backend.repository.ExerciceRepository;
import com.monconcours.backend.repository.FormationRepository;
import com.monconcours.backend.repository.ActualiteRepository;
import com.monconcours.backend.dto.AjouterFavoriRequest;
import java.time.LocalDate;
import com.monconcours.backend.dto.FavoriResponse;
@Service
public class FavoriService {

    private final FavoriRepository favoriRepository;
    private final QCMRepository qcmRepository;
    private final ConcoursRepository concoursRepository;
    private final ExerciceRepository exerciceRepository;
    private final FormationRepository formationRepository;
    private final ActualiteRepository actualiteRepository;

    public FavoriService(FavoriRepository favoriRepository, QCMRepository qcmRepository, ConcoursRepository concoursRepository, ExerciceRepository exerciceRepository, FormationRepository formationRepository, ActualiteRepository actualiteRepository) {
        this.favoriRepository = favoriRepository;
        this.qcmRepository = qcmRepository;
        this.concoursRepository = concoursRepository;
        this.exerciceRepository = exerciceRepository;
        this.formationRepository = formationRepository;
        this.actualiteRepository = actualiteRepository;
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

    public List<Favori> obtenirFavorisEtudiant(Etudiant etudiant) {
        return favoriRepository.findByEtudiant(etudiant);
    }
        public Favori ajouterFavoriSecurise(
                Etudiant etudiant,
                AjouterFavoriRequest request) {

            Favori favori = new Favori();

            favori.setEtudiant(etudiant);
            favori.setDateAjout(LocalDate.now());

            String type = request.getTypeContenu().toUpperCase();

            switch (type) {

                case "QCM":
                    favori.setQcm(
                            qcmRepository.findById(request.getContenuId())
                                    .orElseThrow(() -> new RuntimeException("QCM non trouvé"))
                    );
                    break;

                case "CONCOURS":
                    favori.setConcours(
                            concoursRepository.findById(request.getContenuId())
                                    .orElseThrow(() -> new RuntimeException("Concours non trouvé"))
                    );
                    break;

                case "EXERCICE":
                    favori.setExercice(
                            exerciceRepository.findById(request.getContenuId())
                                    .orElseThrow(() -> new RuntimeException("Exercice non trouvé"))
                    );
                    break;

                case "FORMATION":
                    favori.setFormation(
                            formationRepository.findById(request.getContenuId())
                                    .orElseThrow(() -> new RuntimeException("Formation non trouvée"))
                    );
                    break;

                case "ACTUALITE":
                    favori.setActualite(
                            actualiteRepository.findById(request.getContenuId())
                                    .orElseThrow(() -> new RuntimeException("Actualité non trouvée"))
                    );
                    break;

                default:
                    throw new RuntimeException("Type de contenu invalide");
            }

            return favoriRepository.save(favori);
        }

    public FavoriResponse convertirEnResponse(Favori favori) {

        if (favori.getQcm() != null) {
            return new FavoriResponse(
                    favori.getId(),
                    favori.getDateAjout(),
                    "QCM",
                    favori.getQcm().getId(),
                    favori.getQcm().getTitre()
            );
        }

        if (favori.getConcours() != null) {
            return new FavoriResponse(
                    favori.getId(),
                    favori.getDateAjout(),
                    "CONCOURS",
                    favori.getConcours().getId(),
                    favori.getConcours().getNom()
            );
        }

        if (favori.getExercice() != null) {
            return new FavoriResponse(
                    favori.getId(),
                    favori.getDateAjout(),
                    "EXERCICE",
                    favori.getExercice().getId(),
                    favori.getExercice().getTitre()
            );
        }

        if (favori.getFormation() != null) {
            return new FavoriResponse(
                    favori.getId(),
                    favori.getDateAjout(),
                    "FORMATION",
                    favori.getFormation().getId(),
                    favori.getFormation().getTitre()
            );
        }

        if (favori.getActualite() != null) {
            return new FavoriResponse(
                    favori.getId(),
                    favori.getDateAjout(),
                    "ACTUALITE",
                    favori.getActualite().getId(),
                    favori.getActualite().getTitre()
            );
        }

        throw new RuntimeException("Favori sans contenu");
    }

    public List<FavoriResponse> obtenirFavorisEtudiantResponse(Etudiant etudiant) {
        return favoriRepository.findByEtudiant(etudiant)
                .stream()
                .map(this::convertirEnResponse)
                .toList();
    }

    public void supprimerFavoriEtudiant(Integer favoriId, Etudiant etudiant) {

        Favori favori = favoriRepository.findById(favoriId)
                .orElseThrow(() -> new RuntimeException("Favori non trouvé"));

        if (!favori.getEtudiant().getId().equals(etudiant.getId())) {
            throw new RuntimeException("Vous ne pouvez pas supprimer ce favori");
        }

        favoriRepository.delete(favori);
    }

}
