package com.monconcours.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Favori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateAjout;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_concours")
    private Concours concours;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_exercice")
    private Exercice exercice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_formation")
    private Formation formation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_qcm")
    private QCM qcm;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_actualite")
    private Actualite actualite;
                                /*      Constructeur        */
    public Favori() {
    }

    public Favori(LocalDate dateAjout, Etudiant etudiant, Concours concours,
                  Exercice exercice, Formation formation,
                  QCM qcm, Actualite actualite) {
        this.dateAjout = dateAjout;
        this.etudiant = etudiant;
        this.concours = concours;
        this.exercice = exercice;
        this.formation = formation;
        this.qcm = qcm;
        this.actualite = actualite;
    }
                            /*      Getter & Setter     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Concours getConcours() {
        return concours;
    }

    public void setConcours(Concours concours) {
        this.concours = concours;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public QCM getQcm() {
        return qcm;
    }

    public void setQcm(QCM qcm) {
        this.qcm = qcm;
    }

    public Actualite getActualite() {
        return actualite;
    }

    public void setActualite(Actualite actualite) {
        this.actualite = actualite;
    }
}
