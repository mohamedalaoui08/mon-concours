package com.monconcours.backend.service;

import com.monconcours.backend.entity.QCM;
import com.monconcours.backend.repository.QCMRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.monconcours.backend.repository.ChoixRepository;
import com.monconcours.backend.entity.Choix;
import com.monconcours.backend.entity.Resultat;
import com.monconcours.backend.entity.Etudiant;
import com.monconcours.backend.repository.ResultatRepository;
import java.time.LocalDate;

@Service
public class QCMService {

    private final QCMRepository qcmRepository;
    private final ChoixRepository choixRepository;
    private final ResultatRepository resultatRepository;
    private final AbonnementService abonnementService;

    public QCMService(
            QCMRepository qcmRepository,
            ChoixRepository choixRepository,
            ResultatRepository resultatRepository,
            AbonnementService abonnementService) {

        this.qcmRepository = qcmRepository;
        this.choixRepository = choixRepository;
        this.resultatRepository = resultatRepository;
        this.abonnementService = abonnementService;
    }

    public QCM ajouterQCM(QCM qcm) {
        return qcmRepository.save(qcm);
    }

    public List<QCM> obtenirTousLesQCM() {
        return qcmRepository.findAll();
    }

    public Optional<QCM> obtenirQCMParId(Integer id) {
        return qcmRepository.findById(id);
    }

    public QCM modifierQCM(Integer id, QCM nouveauQCM) {

        QCM qcmExistant = qcmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QCM non trouvé"));

        qcmExistant.setTitre(nouveauQCM.getTitre());
        qcmExistant.setDuree(nouveauQCM.getDuree());
        qcmExistant.setNiveau(nouveauQCM.getNiveau());

        return qcmRepository.save(qcmExistant);
    }

    public void supprimerQCM(Integer id) {
        qcmRepository.deleteById(id);
    }

    public int calculerNombreBonnesReponses(Integer qcmId, List<Integer> choixIds) {

        List<Choix> choixSelectionnes = choixRepository.findAllById(choixIds);

        int bonnesReponses = 0;

        for (Choix choix : choixSelectionnes) {

            if (choix.getQuestion() != null
                    && choix.getQuestion().getQcm() != null
                    && choix.getQuestion().getQcm().getId().equals(qcmId)
                    && choix.isEstCorrecte()) {

                bonnesReponses++;
            }
        }

        return bonnesReponses;
    }
    public float calculerScore(Integer qcmId, List<Integer> choixIds) {

        QCM qcm = qcmRepository.findById(qcmId)
                .orElseThrow(() -> new RuntimeException("QCM non trouvé"));

        int nombreQuestions = qcm.getQuestions().size();

        if (nombreQuestions == 0) {
            return 0;
        }

        int bonnesReponses = calculerNombreBonnesReponses(qcmId, choixIds);

        return ((float) bonnesReponses / nombreQuestions) * 20;
    }

    public Resultat enregistrerResultat(
            Integer qcmId,
            Etudiant etudiant,
            List<Integer> choixIds) {
        if (abonnementService.obtenirAbonnementActif(etudiant).isEmpty()) {
            throw new RuntimeException(
                    "Un abonnement actif est nécessaire pour passer un QCM"
            );
        }

        QCM qcm = qcmRepository.findById(qcmId)
                .orElseThrow(() -> new RuntimeException("QCM non trouvé"));

        float score = calculerScore(qcmId, choixIds);

        Resultat resultat = new Resultat();

        resultat.setScore(score);
        resultat.setDatePassage(LocalDate.now());
        resultat.setQcm(qcm);
        resultat.setEtudiant(etudiant);

        return resultatRepository.save(resultat);
    }
}
