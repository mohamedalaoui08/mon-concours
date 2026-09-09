package com.monconcours.backend.controller;

import com.monconcours.backend.service.PasswordResetService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/mot-de-passe-oublie")
    public String motDePasseOublie(@RequestParam String email) {

        return passwordResetService.creerToken(email);
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    public String reinitialiserMotDePasse(
            @RequestParam String token,
            @RequestParam String nouveauMotDePasse) {

        return passwordResetService.reinitialiserMotDePasse(
                token,
                nouveauMotDePasse
        );
    }
}