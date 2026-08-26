package com.monconcours.backend.service;

import com.monconcours.backend.entity.Utilisateur;
import com.monconcours.backend.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.monconcours.backend.entity.Admin;
import com.monconcours.backend.entity.Etudiant;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        String role;

        if (utilisateur instanceof Admin) {
            role = "ADMIN";
        } else if (utilisateur instanceof Etudiant) {
            role = "ETUDIANT";
        } else {
            role = "USER";
        }
        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .roles(role)
                .build();
    }
}
