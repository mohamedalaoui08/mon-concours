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
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import com.monconcours.backend.service.AbonnementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@RestController
public class ConcoursController {

    private final ConcoursService concoursService;
    private final PdfService pdfService;
    private final EtudiantRepository etudiantRepository;
    private final AbonnementService abonnementService;

    public ConcoursController(
            ConcoursService concoursService,
            PdfService pdfService,
            EtudiantRepository etudiantRepository,
            AbonnementService abonnementService) {

        this.concoursService = concoursService;
        this.pdfService = pdfService;
        this.etudiantRepository = etudiantRepository;
        this.abonnementService = abonnementService;
    }

    @GetMapping("/concours")
    public List<Concours> obtenirTousLesConcours(Authentication authentication) {

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return concoursService.obtenirTousLesConcours();
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        var abonnement = abonnementService.obtenirAbonnementActif(etudiant);

        if (abonnement.isPresent()
                && abonnement.get().getOffreAbonnement().isAccesConcours()) {

            return concoursService.obtenirTousLesConcours();
        }

        return concoursService.obtenirConcoursPublics();
    }

    @GetMapping("/concours/{id}")
    public Optional<Concours> obtenirConcoursParId(
            @PathVariable Integer id,
            Authentication authentication) {

        Concours concours = concoursService.obtenirConcoursParId(id)
                .orElseThrow(() -> new RuntimeException("Concours non trouvé"));

        boolean estAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (estAdmin) {
            return Optional.of(concours);
        }

        if (concours.isPublicConcours()) {
            return Optional.of(concours);
        }

        String email = authentication.getName();

        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        var abonnement = abonnementService.obtenirAbonnementActif(etudiant);

        if (abonnement.isPresent()
                && abonnement.get().getOffreAbonnement().isAccesConcours()) {

            return Optional.of(concours);
        }

        throw new RuntimeException(
                "Votre abonnement ne permet pas l'accès à ce concours"
        );
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
            @PathVariable Integer id,
            Authentication authentication) {

        Concours concours = concoursService.obtenirConcoursParId(id)
                .orElseThrow(() -> new RuntimeException("Concours non trouvé"));

        // Concours public : téléchargement autorisé pour tout le monde
        if (!concours.isPublicConcours()) {

            // Concours privé : il faut être connecté
            if (authentication == null) {
                throw new RuntimeException(
                        "Vous devez être abonné pour télécharger ce concours"
                );
            }

            boolean estAdmin = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));

            // Si ce n'est pas un admin, on vérifie l'abonnement étudiant
            if (!estAdmin) {

                String email = authentication.getName();

                Etudiant etudiant = etudiantRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Etudiant non trouvé")
                        );

                var abonnement = abonnementService.obtenirAbonnementActif(etudiant);

                if (abonnement.isEmpty()
                        || !abonnement.get()
                        .getOffreAbonnement()
                        .isAccesConcours()) {

                    throw new RuntimeException(
                            "Votre abonnement ne permet pas de télécharger ce concours"
                    );
                }
            }
        }

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
