package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Question;
import com.monconcours.backend.service.QuestionService;
import org.springframework.web.bind.annotation.*;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final EtudiantRepository etudiantRepository;
    private final AbonnementService abonnementService;

    public QuestionController(
            QuestionService questionService,
            EtudiantRepository etudiantRepository,
            AbonnementService abonnementService) {

        this.questionService = questionService;
        this.etudiantRepository = etudiantRepository;
        this.abonnementService = abonnementService;
    }

    @GetMapping("/questions")
    public List<Question> obtenirToutesLesQuestions(Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return questionService.obtenirToutesLesQuestions();
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        if (abonnementService.obtenirAbonnementActif(etudiant).isEmpty()) {
            throw new RuntimeException(
                    "Un abonnement actif est nécessaire pour consulter les questions"
            );
        }

        return questionService.obtenirToutesLesQuestions();
    }

    @GetMapping("/questions/{id}")
    public Optional<Question> obtenirQuestionParId(
            @PathVariable Integer id,
            Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return questionService.obtenirQuestionParId(id);
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        if (abonnementService.obtenirAbonnementActif(etudiant).isEmpty()) {
            throw new RuntimeException(
                    "Un abonnement actif est nécessaire pour consulter les questions"
            );
        }

        return questionService.obtenirQuestionParId(id);
    }
    @PostMapping("/questions")
    public Question ajouterQuestion(@RequestBody Question question) {
        return questionService.ajouterQuestion(question);
    }

    @PutMapping("/questions/{id}")
    public Question modifierQuestion(@PathVariable Integer id,
                                     @RequestBody Question question) {
        return questionService.modifierQuestion(id, question);
    }

    @DeleteMapping("/questions/{id}")
    public void supprimerQuestion(@PathVariable Integer id) {
        questionService.supprimerQuestion(id);
    }
}