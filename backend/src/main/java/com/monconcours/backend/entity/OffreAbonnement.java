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
    private boolean accesConcours;
    private boolean accesQcm;
    private boolean accesExercices;
    private boolean accesFormations;

                    /*      Constructeur        */

    public OffreAbonnement() {
    }

    public OffreAbonnement(
            String nom,
            String description,
            double prix,
            int dureeJours,
            boolean accesConcours,
            boolean accesQcm,
            boolean accesExercices,
            boolean accesFormations) {

        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.dureeJours = dureeJours;
        this.accesConcours = accesConcours;
        this.accesQcm = accesQcm;
        this.accesExercices = accesExercices;
        this.accesFormations = accesFormations;
    }            /*      Getter      */

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

    public boolean isAccesConcours() {
        return accesConcours;
    }

    public boolean isAccesQcm() {
        return accesQcm;
    }

    public boolean isAccesExercices() {
        return accesExercices;
    }

    public boolean isAccesFormations() {
        return accesFormations;
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

    public void setAccesConcours(boolean accesConcours) {
        this.accesConcours = accesConcours;
    }

    public void setAccesQcm(boolean accesQcm) {
        this.accesQcm = accesQcm;
    }

    public void setAccesExercices(boolean accesExercices) {
        this.accesExercices = accesExercices;
    }

    public void setAccesFormations(boolean accesFormations) {
        this.accesFormations = accesFormations;
    }
}