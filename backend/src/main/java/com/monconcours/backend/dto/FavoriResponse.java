package com.monconcours.backend.dto;

import java.time.LocalDate;

public class FavoriResponse {

    private Integer id;
    private LocalDate dateAjout;
    private String typeContenu;
    private Integer contenuId;
    private String titre;

    public FavoriResponse() {
    }

    public FavoriResponse(
            Integer id,
            LocalDate dateAjout,
            String typeContenu,
            Integer contenuId,
            String titre) {

        this.id = id;
        this.dateAjout = dateAjout;
        this.typeContenu = typeContenu;
        this.contenuId = contenuId;
        this.titre = titre;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public String getTypeContenu() {
        return typeContenu;
    }

    public Integer getContenuId() {
        return contenuId;
    }

    public String getTitre() {
        return titre;
    }
}