package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Concours;
import com.monconcours.backend.service.ConcoursService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.service.PdfService;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class ConcoursController {

    private final ConcoursService concoursService;
    private final PdfService pdfService;

    public ConcoursController(
            ConcoursService concoursService,
            PdfService pdfService) {

        this.concoursService = concoursService;
        this.pdfService = pdfService;
    }

    @GetMapping("/concours")
    public List<Concours> obtenirTousLesConcours() {
        return concoursService.obtenirTousLesConcours();
    }

    @GetMapping("/concours/{id}")
    public Optional<Concours> obtenirConcoursParId(@PathVariable Integer id) {
        return concoursService.obtenirConcoursParId(id);
    }

    @PostMapping("/concours")
    public Concours ajouterConcours(@RequestBody Concours concours) {
        return concoursService.ajouterConcours(concours);
    }

    @PutMapping("/concours/{id}")
    public Concours modifierConcours(@PathVariable Integer id,
                                     @RequestBody Concours concours) {
        return concoursService.modifierConcours(id, concours);
    }

    @DeleteMapping("/concours/{id}")
    public void supprimerConcours(@PathVariable Integer id) {
        concoursService.supprimerConcours(id);
    }

    @GetMapping("/concours/public")
    public List<Concours> obtenirConcoursPublics() {
        return concoursService.obtenirConcoursPublics();
    }

    @PostMapping("/concours/{id}/pdf")
    public Concours ajouterPdfConcours(
            @PathVariable Integer id,
            @RequestParam("fichier") MultipartFile fichier) throws IOException {

        Concours concours = concoursService.obtenirConcoursParId(id)
                .orElseThrow(() -> new RuntimeException("Concours non trouvé"));

        String nomFichier = pdfService.enregistrerPdf(fichier);

        concours.setFichierPdf(nomFichier);

        return concoursService.modifierConcours(id, concours);
    }

    @GetMapping("/concours/{id}/pdf")
    public ResponseEntity<Resource> telechargerPdfConcours(
            @PathVariable Integer id) {

        Concours concours = concoursService.obtenirConcoursParId(id)
                .orElseThrow(() -> new RuntimeException("Concours non trouvé"));

        if (concours.getFichierPdf() == null) {
            throw new RuntimeException("Aucun PDF associé à ce concours");
        }

        Resource resource = pdfService.chargerPdf(concours.getFichierPdf());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + concours.getFichierPdf() + "\""
                )
                .body(resource);
    }
}
