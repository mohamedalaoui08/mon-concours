package com.monconcours.backend.service;

import com.monconcours.backend.entity.Admin;
import com.monconcours.backend.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // CREATE
    public Admin ajouterAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // READ - tous
    public List<Admin> obtenirTousLesAdmins() {
        return adminRepository.findAll();
    }

    // READ - par id
    public Optional<Admin> obtenirAdminParId(Integer id) {
        return adminRepository.findById(id);
    }

    // UPDATE
    public Admin modifierAdmin(Integer id, Admin nouvelAdmin) {

        Admin adminExistant = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        adminExistant.setNom(nouvelAdmin.getNom());
        adminExistant.setPrenom(nouvelAdmin.getPrenom());
        adminExistant.setEmail(nouvelAdmin.getEmail());
        adminExistant.setMotDePasse(nouvelAdmin.getMotDePasse());

        return adminRepository.save(adminExistant);
    }

    // DELETE
    public void supprimerAdmin(Integer id) {
        adminRepository.deleteById(id);
    }
}
