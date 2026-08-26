package com.monconcours.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DemandeInscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String niveau;
    private String statut;

                            /*      Constructeur        */

    public DemandeInscription() {
    }

    public DemandeInscription(Integer id, String nom, String prenom, String email, LocalDate dateNaissance, String niveau, String statut) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.niveau = niveau;
        this.statut = statut;
    }

                            /*      Getter      */

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getStatut() {
        return statut;
    }
                                /*         Setter       */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}