package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Admin;
import com.monconcours.backend.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // CREATE
    @PostMapping("/admins")
    public Admin ajouterAdmin(@RequestBody Admin admin) {
        return adminService.ajouterAdmin(admin);
    }

    // READ - tous
    @GetMapping("/admins")
    public List<Admin> obtenirTousLesAdmins() {
        return adminService.obtenirTousLesAdmins();
    }

    // READ - par id
    @GetMapping("/admins/{id}")
    public Optional<Admin> obtenirAdminParId(@PathVariable Integer id) {
        return adminService.obtenirAdminParId(id);
    }

    // UPDATE
    @PutMapping("/admins/{id}")
    public Admin modifierAdmin(@PathVariable Integer id,
                               @RequestBody Admin admin) {
        return adminService.modifierAdmin(id, admin);
    }

    // DELETE
    @DeleteMapping("/admins/{id}")
    public void supprimerAdmin(@PathVariable Integer id) {
        adminService.supprimerAdmin(id);
    }
}