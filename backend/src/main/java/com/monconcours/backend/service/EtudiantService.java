package com.monconcours.backend.service;

import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import com.monconcours.backend.repository.FavoriRepository;
import com.monconcours.backend.repository.ResultatRepository;
import com.monconcours.backend.repository.AbonnementRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;
    private final FavoriRepository favoriRepository;
    private final ResultatRepository resultatRepository;
    private final AbonnementRepository abonnementRepository;

    public EtudiantService(
            EtudiantRepository etudiantRepository,
            PasswordEncoder passwordEncoder,
            FavoriRepository favoriRepository,
            ResultatRepository resultatRepository,
            AbonnementRepository abonnementRepository) {

        this.etudiantRepository = etudiantRepository;
        this.passwordEncoder = passwordEncoder;
        this.favoriRepository = favoriRepository;
        this.resultatRepository = resultatRepository;
        this.abonnementRepository = abonnementRepository;
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
    @Transactional
    public void supprimerEtudiant(Integer id) {

        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        favoriRepository.deleteAll(
                favoriRepository.findByEtudiant(etudiant)
        );

        resultatRepository.deleteAll(
                resultatRepository.findByEtudiant(etudiant)
        );

        abonnementRepository.deleteAll(
                abonnementRepository.findByEtudiant(etudiant)
        );

        etudiantRepository.delete(etudiant);
    }
}