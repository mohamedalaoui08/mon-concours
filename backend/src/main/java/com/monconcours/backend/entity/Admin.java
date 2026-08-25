package com.monconcours.backend.entity;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Utilisateur {
                    /*  Constructeur    */

    public Admin() {
    }

    public Admin(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }
}