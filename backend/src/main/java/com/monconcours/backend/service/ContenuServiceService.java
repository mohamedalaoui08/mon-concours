package com.monconcours.backend.service;

import com.monconcours.backend.entity.ContenuService;
import com.monconcours.backend.repository.ContenuServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenuServiceService {

    private final ContenuServiceRepository contenuServiceRepository;

    public ContenuServiceService(
            ContenuServiceRepository contenuServiceRepository) {
        this.contenuServiceRepository = contenuServiceRepository;
    }

    public List<ContenuService> obtenirTous() {
        return contenuServiceRepository.findAllByOrderByOrdreAsc();
    }

    public Optional<ContenuService> obtenirParId(Integer id) {
        return contenuServiceRepository.findById(id);
    }

    public ContenuService ajouter(ContenuService contenu) {

        ContenuService dernier = contenuServiceRepository.findTopByOrderByOrdreDesc();

        if (dernier == null) {
            contenu.setOrdre(1);
        } else {
            contenu.setOrdre(dernier.getOrdre() + 1);
        }

        return contenuServiceRepository.save(contenu);
    }

    public ContenuService modifier(Integer id, ContenuService nouveauContenu) {

        ContenuService contenu = contenuServiceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contenu Service non trouvé")
                );

        contenu.setTitre(nouveauContenu.getTitre());
        contenu.setDescription(nouveauContenu.getDescription());

        return contenuServiceRepository.save(contenu);
    }

    public void supprimer(Integer id) {

        if (!contenuServiceRepository.existsById(id)) {
            throw new RuntimeException("Contenu Service non trouvé");
        }

        contenuServiceRepository.deleteById(id);
    }
}