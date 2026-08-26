package com.monconcours.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String enonce;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_qcm")
    private QCM qcm;

    @OneToMany(mappedBy = "question")
    private List<Choix> choix;
                            /*  Constructeur */

    public Question() {
    }

    public Question(String enonce, QCM qcm) {
        this.enonce = enonce;
        this.qcm = qcm;
    }
                                /*  Getter  */

    public Integer getId() {
        return id;
    }

    public String getEnonce() {
        return enonce;
    }

    public QCM getQcm() {
        return qcm;
    }

    public List<Choix> getChoix() {
        return choix;
    }

    /*  Setter  */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public void setQcm(QCM qcm) {
        this.qcm = qcm;
    }

    public void setChoix(List<Choix> choix) {
        this.choix = choix;
    }
}
