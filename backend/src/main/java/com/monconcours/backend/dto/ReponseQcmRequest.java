package com.monconcours.backend.dto;

import java.util.List;

public class ReponseQcmRequest {

    private List<Integer> choixIds;

    public ReponseQcmRequest() {
    }

    public List<Integer> getChoixIds() {
        return choixIds;
    }

    public void setChoixIds(List<Integer> choixIds) {
        this.choixIds = choixIds;
    }
}