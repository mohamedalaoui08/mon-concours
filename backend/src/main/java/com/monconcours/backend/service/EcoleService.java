package com.monconcours.backend.service;

import com.monconcours.backend.repository.EcoleRepository;
import org.springframework.stereotype.Service;
import com.monconcours.backend.entity.Ecole;
import java.util.Optional;
import java.util.List;

@Service
public class EcoleService {

    private final EcoleRepository ecoleRepository;

    public EcoleService(EcoleRepository ecoleRepository) {
        this.ecoleRepository = ecoleRepository;
    }

    public Ecole ajouterEcole(Ecole ecole) {
        return ecoleRepository.save(ecole);
    }

    public List<Ecole> obtenirToutesLesEcoles() {
        return ecoleRepository.findAll();
    }

    public Optional<Ecole> obtenirEcoleParId(Integer id) {
        return ecoleRepository.findById(id);
    }
    public void supprimerEcole(Integer id) {
        ecoleRepository.deleteById(id);
    }

    public Ecole modifierEcole(Integer id, Ecole nouvelleEcole) {

        Ecole ecoleExistante = ecoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));

        ecoleExistante.setNom(nouvelleEcole.getNom());
        ecoleExistante.setDescription(nouvelleEcole.getDescription());

        return ecoleRepository.save(ecoleExistante);
    }


}