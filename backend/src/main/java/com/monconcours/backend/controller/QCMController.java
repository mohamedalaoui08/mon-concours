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

@RestController
public class QCMController {

    private final QCMService qcmService;
    private final EtudiantRepository etudiantRepository;

    public QCMController(
            QCMService qcmService,
            EtudiantRepository etudiantRepository) {

        this.qcmService = qcmService;
        this.etudiantRepository = etudiantRepository;
    }

    @GetMapping("/qcms")
    public List<QCM> obtenirTousLesQCM() {
        return qcmService.obtenirTousLesQCM();
    }

    @GetMapping("/qcms/{id}")
    public Optional<QCM> obtenirQCMParId(@PathVariable Integer id) {
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

        return qcmService.enregistrerResultat(
                id,
                etudiant,
                reponse.getChoixIds()
        );
    }
}
