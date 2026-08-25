package com.monconcours.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private float score;
    private LocalDate datePassage;

    @ManyToOne
    @JoinColumn(name = "id_qcm")
    private QCM qcm;

    @ManyToOne
    @JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;

                /*  Constructeur    */

    public Resultat() {
    }

    public Resultat(float score, LocalDate datePassage, QCM qcm, Etudiant etudiant) {
        this.score = score;
        this.datePassage = datePassage;
        this.qcm = qcm;
        this.etudiant = etudiant;
    }
                /* Getter */

    public Integer getId() {
        return id;
    }

    public float getScore() {
        return score;
    }

    public LocalDate getDatePassage() {
        return datePassage;
    }

    public QCM getQcm() {
        return qcm;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }
                    /*  Setter */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setDatePassage(LocalDate datePassage) {
        this.datePassage = datePassage;
    }

    public void setQcm(QCM qcm) {
        this.qcm = qcm;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
