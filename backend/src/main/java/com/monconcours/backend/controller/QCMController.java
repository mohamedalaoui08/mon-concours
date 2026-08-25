package com.monconcours.backend.controller;

import com.monconcours.backend.entity.QCM;
import com.monconcours.backend.service.QCMService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class QCMController {

    private final QCMService qcmService;

    public QCMController(QCMService qcmService) {
        this.qcmService = qcmService;
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
}
