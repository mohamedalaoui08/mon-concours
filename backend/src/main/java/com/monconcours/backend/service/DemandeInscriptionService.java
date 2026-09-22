package com.monconcours.backend.service;

import com.monconcours.backend.entity.DemandeInscription;
import com.monconcours.backend.repository.DemandeInscriptionRepository;
import org.springframework.stereotype.Service;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import com.monconcours.backend.entity.OffreAbonnement;
import com.monconcours.backend.repository.OffreAbonnementRepository;
import com.monconcours.backend.entity.Abonnement;
import com.monconcours.backend.repository.AbonnementRepository;
import java.time.LocalDate;
import com.monconcours.backend.entity.PasswordResetToken;
import com.monconcours.backend.repository.PasswordResetTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
public class DemandeInscriptionService {

    private final DemandeInscriptionRepository demandeInscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom();
    private final OffreAbonnementRepository offreAbonnementRepository;
    private final AbonnementRepository abonnementRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public DemandeInscriptionService(
            DemandeInscriptionRepository demandeInscriptionRepository,
            EtudiantRepository etudiantRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder,
            OffreAbonnementRepository offreAbonnementRepository,
            AbonnementRepository abonnementRepository,
            PasswordResetTokenRepository passwordResetTokenRepository) {

        this.demandeInscriptionRepository = demandeInscriptionRepository;
        this.etudiantRepository = etudiantRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.offreAbonnementRepository = offreAbonnementRepository;
        this.abonnementRepository = abonnementRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    // CREATE
    public DemandeInscription ajouterDemande(DemandeInscription demande) {

        if (!domaineEmailExiste(demande.getEmail())) {
            throw new RuntimeException(
                    "L'adresse email utilise un domaine inexistant"
            );
        }

        if (demandeInscriptionRepository.existsByEmail(demande.getEmail())) {
            throw new RuntimeException("Une demande avec cet email existe déjà");
        }

        if (etudiantRepository.findByEmail(demande.getEmail()).isPresent()) {
            throw new RuntimeException("Un étudiant avec cet email existe déjà");
        }

        if (demande.getOffreAbonnement() == null ||
                demande.getOffreAbonnement().getId() == null) {
            throw new RuntimeException("Vous devez choisir une offre d'abonnement");
        }

        OffreAbonnement offre = offreAbonnementRepository
                .findById(demande.getOffreAbonnement().getId())
                .orElseThrow(() -> new RuntimeException("Offre d'abonnement introuvable"));

        if (!offre.isActive()) {
            throw new RuntimeException("Cette offre d'abonnement n'est plus disponible");
        }

        demande.setOffreAbonnement(offre);

        demande.setStatut("EN_ATTENTE");

        return demandeInscriptionRepository.save(demande);
    }
    // READ - toutes
    public List<DemandeInscription> obtenirToutesLesDemandes() {
        return demandeInscriptionRepository.findAll();
    }

    // READ - par id
    public Optional<DemandeInscription> obtenirDemandeParId(Integer id) {
        return demandeInscriptionRepository.findById(id);
    }

    // DELETE
    public void supprimerDemande(Integer id) {
        demandeInscriptionRepository.deleteById(id);
    }
    // ACCEPTER DEMANDE D'INSCRIPTION
    public DemandeInscription accepterDemande(Integer id) {

        DemandeInscription demande = demandeInscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvee"));

        if ("ACCEPTEE".equals(demande.getStatut())) {
            throw new RuntimeException("Cette demande a déjà été acceptée");
        }

        if (etudiantRepository.findByEmail(demande.getEmail()).isPresent()) {
            throw new RuntimeException("Un étudiant avec cet email existe déjà");
        }

        // Génération du code actuel
        String codeInitial =
                String.valueOf(secureRandom.nextInt(900000) + 100000);

        String codeEncode = passwordEncoder.encode(codeInitial);

        // Création de l'étudiant
        Etudiant etudiant = new Etudiant(
                demande.getNom(),
                demande.getPrenom(),
                demande.getEmail(),
                codeEncode,
                demande.getDateNaissance(),
                demande.getNiveau()
        );

        etudiant = etudiantRepository.save(etudiant);

        // Création automatique de l'abonnement choisi
        LocalDate dateDebut = LocalDate.now();

        LocalDate dateFin = dateDebut.plusDays(
                demande.getOffreAbonnement().getDureeJours()
        );

        Abonnement abonnement = new Abonnement(
                dateDebut,
                dateFin,
                "ACTIF",
                etudiant
        );

        abonnement.setOffreAbonnement(
                demande.getOffreAbonnement()
        );

        abonnementRepository.save(abonnement);

        // Création du token pour choisir son propre mot de passe
        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken =
                new PasswordResetToken();

        passwordResetToken.setEmail(demande.getEmail());
        passwordResetToken.setToken(token);
        passwordResetToken.setDateExpiration(
                LocalDateTime.now().plusMinutes(15)
        );

        passwordResetTokenRepository.save(passwordResetToken);

        // Même page que "Mot de passe oublié"
        String lien =
                "http://localhost:4200/reinitialiser-mot-de-passe?token="
                        + token;

        // Un seul email : code + lien
        emailService.envoyerCodeConnexion(
                demande.getEmail(),
                codeInitial,
                lien
        );

        demande.setStatut("ACCEPTEE");

        return demandeInscriptionRepository.save(demande);
    }

    public DemandeInscription refuserDemande(Integer id) {

        DemandeInscription demande = demandeInscriptionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        demande.setStatut("REFUSEE");

        return demandeInscriptionRepository.save(demande);
    }

    private boolean domaineEmailExiste(String email) {

        try {
            String domaine = email.substring(email.indexOf("@") + 1);

            DirContext contexte = new InitialDirContext();

            Attributes attributs = contexte.getAttributes(
                    "dns:/" + domaine,
                    new String[]{"MX"}
            );

            return attributs.get("MX") != null;

        } catch (Exception e) {
            return false;
        }
    }
}
