package com.monconcours.backend.service;

import com.monconcours.backend.entity.Formation;
import com.monconcours.backend.repository.FormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    // CREATE
    public Formation ajouterFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    // READ - toutes
    public List<Formation> obtenirToutesLesFormations() {
        return formationRepository.findAll();
    }

    // READ - par id
    public Optional<Formation> obtenirFormationParId(Integer id) {
        return formationRepository.findById(id);
    }

    // UPDATE
    public Formation modifierFormation(Integer id, Formation nouvelleFormation) {

        Formation formationExistante = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        formationExistante.setTitre(nouvelleFormation.getTitre());
        formationExistante.setDescription(nouvelleFormation.getDescription());
        formationExistante.setContenu(nouvelleFormation.getContenu());
        formationExistante.setDuree(nouvelleFormation.getDuree());
        formationExistante.setNiveau(nouvelleFormation.getNiveau());
        formationExistante.setDatePublication(nouvelleFormation.getDatePublication());

        return formationRepository.save(formationExistante);
    }

    // DELETE
    public void supprimerFormation(Integer id) {
        formationRepository.deleteById(id);
    }
}
