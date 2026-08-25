package com.monconcours.backend.service;

import com.monconcours.backend.entity.Utilisateur;
import com.monconcours.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    // CREATE
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    // READ - tous
    public List<Utilisateur> obtenirTousLesUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // READ - par id
    public Optional<Utilisateur> obtenirUtilisateurParId(Integer id) {
        return utilisateurRepository.findById(id);
    }

    // UPDATE
    public Utilisateur modifierUtilisateur(Integer id, Utilisateur nouvelUtilisateur) {

        Utilisateur utilisateurExistant = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateurExistant.setNom(nouvelUtilisateur.getNom());
        utilisateurExistant.setPrenom(nouvelUtilisateur.getPrenom());
        utilisateurExistant.setEmail(nouvelUtilisateur.getEmail());
        utilisateurExistant.setMotDePasse(nouvelUtilisateur.getMotDePasse());

        return utilisateurRepository.save(utilisateurExistant);
    }

    // DELETE
    public void supprimerUtilisateur(Integer id) {
        utilisateurRepository.deleteById(id);
    }
}
