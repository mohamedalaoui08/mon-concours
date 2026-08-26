package com.monconcours.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Etudiant extends Utilisateur {

    private LocalDate dateNaissance;
    private String niveau;
    @OneToMany(mappedBy = "etudiant")
    private List<Resultat> resultats;

    @OneToMany(mappedBy = "etudiant")
    private List<Abonnement> abonnements;

    @OneToMany(mappedBy = "etudiant")
    private List<Favori> favoris;
                                /*    getter    */

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getNiveau() {
        return niveau;
    }
                                /*      setter      */

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
                            /*constructeur*/
    public Etudiant() {
                                }
    public Etudiant(String nom, String prenom, String email, String motDePasse, LocalDate dateNaissance, String niveau) {
        super(nom, prenom, email, motDePasse);
        this.dateNaissance = dateNaissance;
        this.niveau = niveau;
    }
}
