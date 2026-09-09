package com.monconcours.backend.service;

import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;

    public EtudiantService(
            EtudiantRepository etudiantRepository,
            PasswordEncoder passwordEncoder) {

        this.etudiantRepository = etudiantRepository;
        this.passwordEncoder = passwordEncoder;
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
        etudiantExistant.setDateNaissance(nouvelEtudiant.getDateNaissance());
        etudiantExistant.setNiveau(nouvelEtudiant.getNiveau());

        return etudiantRepository.save(etudiantExistant);
    }

    // DELETE
    public void supprimerEtudiant(Integer id) {
        etudiantRepository.deleteById(id);
    }


}