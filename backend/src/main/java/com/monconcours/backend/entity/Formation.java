package com.monconcours.backend.entity;

import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDate;

@Entity
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private String description;
    private String contenu;
    private Integer duree;
    private String niveau;
    private LocalDate datePublication;

    @OneToMany(mappedBy = "formation")
    private List<Favori> favori;

                    /*      Constructeur        */

    public Formation() {
    }

    public Formation(String titre, String description, String contenu, Integer duree, String niveau, LocalDate datePublication) {
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
        this.duree = duree;
        this.niveau = niveau;
        this.datePublication = datePublication;
    }
                /*      Getter      */

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getContenu() {
        return contenu;
    }

    public Integer getDuree() {
        return duree;
    }

    public String getNiveau() {
        return niveau;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }
                /*      Setter      */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }
}