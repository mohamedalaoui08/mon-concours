package com.monconcours.backend.entity;

import jakarta.persistence.*;

@Entity
public class OffreAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String description;
    private double prix;
    private int dureeJours;

                    /*      Constructeur        */

    public OffreAbonnement() {
    }

    public OffreAbonnement(String nom, String description, double prix, int dureeJours) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.dureeJours = dureeJours;
    }
                    /*      Getter      */

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public int getDureeJours() {
        return dureeJours;
    }
                /*      Setter      */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setDureeJours(int dureeJours) {
        this.dureeJours = dureeJours;
    }
}