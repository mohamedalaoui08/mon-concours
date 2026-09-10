package com.monconcours.backend.controller;

import com.monconcours.backend.entity.QCM;
import com.monconcours.backend.service.QCMService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.repository.EtudiantRepository;
import com.monconcours.backend.dto.ReponseQcmRequest;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.entity.Resultat;
import org.springframework.security.core.Authentication;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.security.core.GrantedAuthority;

@RestController
public class QCMController {

    private final QCMService qcmService;
    private final EtudiantRepository etudiantRepository;
    private final AbonnementService abonnementService;

    public QCMController(
            QCMService qcmService,
            EtudiantRepository etudiantRepository,
            AbonnementService abonnementService) {

        this.qcmService = qcmService;
        this.etudiantRepository = etudiantRepository;
        this.abonnementService = abonnementService;
    }

    @GetMapping("/qcms")
    public List<QCM> obtenirTousLesQCM(Authentication authentication) {
        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return qcmService.obtenirTousLesQCM();
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        var abonnement = abonnementService.obtenirAbonnementActif(etudiant)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Un abonnement actif est nécessaire pour consulter les QCM"
                        )
                );

        if (!abonnement.getOffreAbonnement().isAccesQcm()) {
            throw new RuntimeException(
                    "Votre abonnement ne permet pas l'accès aux QCM"
            );
        }

        return qcmService.obtenirTousLesQCM();
    }

    @GetMapping("/qcms/{id}")
    public Optional<QCM> obtenirQCMParId(
            @PathVariable Integer id,
            Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return qcmService.obtenirQCMParId(id);
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
        var abonnement = abonnementService.obtenirAbonnementActif(etudiant)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Un abonnement actif est nécessaire pour consulter les QCM"
                        )
                );

        if (!abonnement.getOffreAbonnement().isAccesQcm()) {
            throw new RuntimeException(
                    "Votre abonnement ne permet pas l'accès aux QCM"
            );
        }

        return qcmService.obtenirQCMParId(id);
    }

    @PostMapping("/qcms")
    public QCM ajouterQCM(@RequestBody QCM qcm) {
        return qcmService.ajouterQCM(qcm);
    }

    @PutMapping("/qcms/{id}")
    public QCM modifierQCM(@PathVariable Integer id,
                           @RequestBody QCM qcm) {
        return qcmService.modifierQCM(id, qcm);
    }

    @DeleteMapping("/qcms/{id}")
    public void supprimerQCM(@PathVariable Integer id) {
        qcmService.supprimerQCM(id);
    }

    @PostMapping("/qcms/{id}/passer")
    public Resultat passerQCM(
            @PathVariable Integer id,
            @RequestBody ReponseQcmRequest reponse,
            Authentication authentication) {

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        var abonnement = abonnementService.obtenirAbonnementActif(etudiant)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Un abonnement actif est nécessaire pour passer les QCM"
                        )
                );

        System.out.println("EMAIL = " + email);
        System.out.println("OFFRE = " + abonnement.getOffreAbonnement().getNom());
        System.out.println("ACCES QCM = " + abonnement.getOffreAbonnement().isAccesQcm());

        if (!abonnement.getOffreAbonnement().isAccesQcm()) {
            throw new RuntimeException(
                    "Votre abonnement ne permet pas l'accès aux QCM"
            );
        }

        return qcmService.enregistrerResultat(
                id,
                etudiant,
                reponse.getChoixIds()
        );
    }
}
