package com.monconcours.backend.dto;

public class AjouterFavoriRequest {

    private String typeContenu;
    private Integer contenuId;

    public AjouterFavoriRequest() {
    }

    public String getTypeContenu() {
        return typeContenu;
    }

    public void setTypeContenu(String typeContenu) {
        this.typeContenu = typeContenu;
    }

    public Integer getContenuId() {
        return contenuId;
    }

    public void setContenuId(Integer contenuId) {
        this.contenuId = contenuId;
    }
}
