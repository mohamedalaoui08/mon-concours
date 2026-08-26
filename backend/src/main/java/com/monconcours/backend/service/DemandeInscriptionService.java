package com.monconcours.backend.service;

import com.monconcours.backend.entity.DemandeInscription;
import com.monconcours.backend.repository.DemandeInscriptionRepository;
import org.springframework.stereotype.Service;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.EtudiantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeInscriptionService {

    private final DemandeInscriptionRepository demandeInscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public DemandeInscriptionService(
            DemandeInscriptionRepository demandeInscriptionRepository,
            EtudiantRepository etudiantRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder) {

        this.demandeInscriptionRepository = demandeInscriptionRepository;
        this.etudiantRepository = etudiantRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    // CREATE
    public DemandeInscription ajouterDemande(DemandeInscription demande) {
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

        //String codeInitial = String.valueOf((int) (Math.random() * 900000) + 100000);
        String codeInitial = "123456";
        String codeEncode = passwordEncoder.encode(codeInitial);

        Etudiant etudiant = new Etudiant(
                demande.getNom(),
                demande.getPrenom(),
                demande.getEmail(),
                codeEncode,
                demande.getDateNaissance(),
                demande.getNiveau()
        );

        etudiantRepository.save(etudiant);
        //emailService.envoyerCodeConnexion(demande.getEmail(), codeInitial);
        demande.setStatut("ACCEPTEE");

        return demandeInscriptionRepository.save(demande);
    }
}
