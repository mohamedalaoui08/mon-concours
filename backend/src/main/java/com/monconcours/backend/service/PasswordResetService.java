package com.monconcours.backend.service;

import com.monconcours.backend.entity.PasswordResetToken;
import com.monconcours.backend.repository.PasswordResetTokenRepository;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public PasswordResetService(
            PasswordResetTokenRepository passwordResetTokenRepository,
            EtudiantRepository etudiantRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {

        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.etudiantRepository = etudiantRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public String creerToken(String email) {

        etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email introuvable"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setEmail(email);
        passwordResetToken.setToken(token);
        passwordResetToken.setDateExpiration(
                LocalDateTime.now().plusMinutes(15)
        );

        passwordResetTokenRepository.save(passwordResetToken);

        String lien =
                "http://localhost:4200/reinitialiser-mot-de-passe?token=" + token;

        emailService.envoyerLienReinitialisation(
                email,
                lien
        );

        return "Email de réinitialisation envoyé";
    }

    public String reinitialiserMotDePasse(
            String token,
            String nouveauMotDePasse) {

        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Token invalide"));

        if (passwordResetToken.getDateExpiration()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Token expiré");
        }

        var etudiant = etudiantRepository
                .findByEmail(passwordResetToken.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Etudiant introuvable"));

        etudiant.setMotDePasse(
                passwordEncoder.encode(nouveauMotDePasse)
        );

        etudiantRepository.save(etudiant);

        // Le token ne peut plus être réutilisé
        passwordResetTokenRepository.delete(passwordResetToken);

        return "Mot de passe modifié avec succès";
    }
}