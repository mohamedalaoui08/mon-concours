package com.monconcours.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Actualite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private String contenu;
    private LocalDate datePublication;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_ecole")
    private Ecole ecole;

    @OneToMany(mappedBy = "actualite")
    private List<Favori> favori;

                    /*      Constructeur        */

    public Actualite() {
    }

    public Actualite( String titre, String contenu, LocalDate datePublication, Ecole ecole) {
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.ecole = ecole;
    }
                    /*    Getter      */

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public Ecole getEcole() {
        return ecole;
    }
                        /*      Setter      */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }
}
