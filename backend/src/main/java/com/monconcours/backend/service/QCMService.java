package com.monconcours.backend.service;

import com.monconcours.backend.entity.QCM;
import com.monconcours.backend.repository.QCMRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QCMService {

    private final QCMRepository qcmRepository;

    public QCMService(QCMRepository qcmRepository) {
        this.qcmRepository = qcmRepository;
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
}
