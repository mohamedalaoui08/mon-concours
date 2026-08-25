package com.monconcours.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
    public class Exercice {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String titre;
        private String enonce;
        private String matiere;

        @OneToMany(mappedBy = "exercice")
        private List<Favori> favori;
                        /*  Constructeur    */

        public Exercice() {
        }

        public Exercice(String titre, String enonce, String matiere) {
            this.titre = titre;
            this.enonce = enonce;
            this.matiere = matiere;
        }
                        /*  Getter  */

        public Integer getId() {
            return id;
        }

        public String getTitre() {
            return titre;
        }

        public String getEnonce() {
            return enonce;
        }

        public String getMatiere() {
            return matiere;
        }

                        /*  Setter  */

        public void setId(Integer id) {
            this.id = id;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public void setEnonce(String enonce) {
            this.enonce = enonce;
        }

        public void setMatiere(String matiere) {
            this.matiere = matiere;
        }
    }

