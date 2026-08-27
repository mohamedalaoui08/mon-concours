package com.monconcours.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.monconcours.backend.entity.OffreAbonnement;
@Entity
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_offre_abonnement")
    private OffreAbonnement offreAbonnement;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;

                                /* Constructeur */

    public Abonnement() {
    }
    public void setOffreAbonnement(OffreAbonnement offreAbonnement) {
        this.offreAbonnement = offreAbonnement;
    }
    public Abonnement(LocalDate dateDebut, LocalDate dateFin, String statut, Etudiant etudiant) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.etudiant = etudiant;
    }
                                /*  Getter     */

    public Integer getId() {
        return id;
    }

    public OffreAbonnement getOffreAbonnement() {
        return offreAbonnement;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public String getStatut() {
        return statut;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    /*  Setter    */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}