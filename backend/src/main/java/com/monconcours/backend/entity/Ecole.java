package com.monconcours.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ecole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String description;

    @OneToMany(mappedBy = "ecole")
    private List<Concours> concours;

    @OneToMany(mappedBy = "ecole")
    private List<Actualite> actualites;
                        /*  Constructeur    */

    public Ecole() {
    }

    public Ecole(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
                         /*  Getter  */

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public List<Concours> getConcours() {
        return concours;
    }
                                    /*  Setter  */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConcours(List<Concours> concours) {
        this.concours = concours;
    }
}
