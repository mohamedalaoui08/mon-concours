package com.monconcours.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class QCM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private  Integer duree;
    private String niveau;

    @OneToMany(mappedBy = "qcm")
    private List<Question> questions;

    @OneToMany(mappedBy = "qcm")
    private List<Resultat> resultats;

    @OneToMany(mappedBy = "qcm")
    private List<Favori> favori;
                    /*       Constructeur     */

    public QCM() {
    }

    public QCM(String titre, Integer duree, String niveau) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.niveau = niveau;
    }
                            /*       Getter     */

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Integer getDuree() {
        return duree;
    }

    public String getNiveau() {
        return niveau;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Resultat> getResultats() {
        return resultats;
    }

    /*       Setter     */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setResultats(List<Resultat> resultats) {
        this.resultats = resultats;
    }
}
