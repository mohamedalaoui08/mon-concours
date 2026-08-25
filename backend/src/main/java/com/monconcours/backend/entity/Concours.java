package com.monconcours.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Concours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private LocalDate date;
    private String description;
    private String fichierPdf;
    @ManyToOne
    @JoinColumn(name = "id_ecole")
    private Ecole ecole;

    @OneToMany(mappedBy = "concours")
    private List<Favori> favori;
                        /* Constructeur   */

    public Concours() {
    }

    public Concours(String nom, LocalDate date, String fichierPdf, String description, Ecole ecole) {
        this.nom = nom;
        this.date = date;
        this.fichierPdf = fichierPdf;
        this.description = description;
        this.ecole = ecole;
    }
                        /*  Getter    */

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getFichierPdf() {
        return fichierPdf;
    }

    public Ecole getEcole() {
        return ecole;
    }

                    /*  Setter  */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFichierPdf(String fichierPdf) {
        this.fichierPdf = fichierPdf;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }
}
