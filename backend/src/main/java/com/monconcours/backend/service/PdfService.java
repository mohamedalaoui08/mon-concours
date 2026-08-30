package com.monconcours.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

@Service
public class PdfService {

    private final Path dossierPdf = Paths.get("uploads/pdf");

    public String enregistrerPdf(MultipartFile fichier) throws IOException {

        if (fichier.isEmpty()) {
            throw new RuntimeException("Le fichier PDF est vide");
        }

        if (!"application/pdf".equalsIgnoreCase(fichier.getContentType())) {
            throw new RuntimeException("Le fichier doit être un PDF");
        }

        Files.createDirectories(dossierPdf);

        String nomFichier = UUID.randomUUID() + ".pdf";

        Path destination = dossierPdf.resolve(nomFichier);

        Files.copy(
                fichier.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        return nomFichier;
    }

    public Resource chargerPdf(String nomFichier) {

        try {
            Path dossierNormalise = dossierPdf.toAbsolutePath().normalize();
            Path cheminFichier = dossierNormalise.resolve(nomFichier).normalize();

            if (!cheminFichier.startsWith(dossierNormalise)) {
                throw new RuntimeException("Chemin PDF invalide");
            }

            Resource resource = new UrlResource(cheminFichier.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("PDF non trouvé");
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur lors du chargement du PDF");
        }
    }
}