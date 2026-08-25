package com.monconcours.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Choix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String texte;
    private boolean estCorrecte;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private Question question;
                            /*  Constructeur    */

    public Choix() {
    }

    public Choix( String texte, boolean estCorrecte, Question question) {
        this.texte = texte;
        this.estCorrecte = estCorrecte;
        this.question = question;
    }
                        /*     Getter     */

    public Integer getId() {
        return id;
    }

    public String getTexte() {
        return texte;
    }

    public boolean isEstCorrecte() {
        return estCorrecte;
    }

    public Question getQuestion() {
        return question;
    }

    /*      Setter      */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setEstCorrecte(boolean estCorrecte) {
        this.estCorrecte = estCorrecte;
    }


}