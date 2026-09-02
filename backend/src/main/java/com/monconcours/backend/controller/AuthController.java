package com.monconcours.backend.controller;

import com.monconcours.backend.dto.LoginRequest;
import com.monconcours.backend.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import com.monconcours.backend.entity.Utilisateur;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.monconcours.backend.service.JwtService;
import com.monconcours.backend.entity.Admin;

@RestController
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        boolean motDePasseCorrect = passwordEncoder.matches(
                loginRequest.getMotDePasse(),
                utilisateur.getMotDePasse()
        );

        if (motDePasseCorrect) {
            String role;

            if (utilisateur instanceof Admin) {
                role = "ADMIN";
            } else {
                role = "ETUDIANT";
            }
            return jwtService.genererToken(utilisateur.getEmail(), role);
        } else {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }
    }
}