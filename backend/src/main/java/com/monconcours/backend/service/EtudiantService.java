package com.monconcours.backend.service;

import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    // CREATE
    public Etudiant ajouterEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // READ - tous
    public List<Etudiant> obtenirTousLesEtudiants() {
        return etudiantRepository.findAll();
    }

    // READ - par id
    public Optional<Etudiant> obtenirEtudiantParId(Integer id) {
        return etudiantRepository.findById(id);
    }

    // UPDATE
    public Etudiant modifierEtudiant(Integer id, Etudiant nouvelEtudiant) {

        Etudiant etudiantExistant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        etudiantExistant.setNom(nouvelEtudiant.getNom());
        etudiantExistant.setPrenom(nouvelEtudiant.getPrenom());
        etudiantExistant.setEmail(nouvelEtudiant.getEmail());
        etudiantExistant.setMotDePasse(nouvelEtudiant.getMotDePasse());
        etudiantExistant.setDateNaissance(nouvelEtudiant.getDateNaissance());
        etudiantExistant.setNiveau(nouvelEtudiant.getNiveau());

        return etudiantRepository.save(etudiantExistant);
    }

    // DELETE
    public void supprimerEtudiant(Integer id) {
        etudiantRepository.deleteById(id);
    }
}