package com.monconcours.backend.entity;

import jakarta.persistence.*;

@Entity
public class ContenuService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer ordre;

    public ContenuService() {
    }

    public ContenuService(
            Integer id,
            String titre,
            String description,
            Integer ordre) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.ordre = ordre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }
}