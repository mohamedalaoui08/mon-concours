import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-inscription',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inscription.html',
  styleUrl: './inscription.css'
})
export class Inscription {

  private http = inject(HttpClient);

  offres: any[] = [];
  offreSelectionneeId: number | null = null;

  demande = {
    nom: '',
    prenom: '',
    email: '',
    dateNaissance: '',
    niveau: ''
  };

  message = '';

  ngOnInit() {
    this.http.get<any[]>(
      'http://localhost:8080/offres-abonnement/actives'
    ).subscribe({
      next: (reponse) => {
        this.offres = reponse;
      },
      error: (erreur) => {
        console.log('Erreur chargement offres :', erreur);
      }
    });
  }

  envoyerDemande() {

    if (!this.offreSelectionneeId) {
      this.message = 'Veuillez choisir un abonnement.';
      return;
    }

const offreChoisie = this.offres.find(
  offre => offre.id === this.offreSelectionneeId
);

const demandeAvecOffre = {
  ...this.demande,
  offreAbonnement: offreChoisie
};

    this.http.post(
      'http://localhost:8080/demandes-inscription',
      demandeAvecOffre
    ).subscribe({

      next: () => {

        this.message =
          'Votre demande d’inscription a bien été envoyée.';

        this.demande = {
          nom: '',
          prenom: '',
          email: '',
          dateNaissance: '',
          niveau: ''
        };

        this.offreSelectionneeId = null;
      },

      error: (erreur) => {
        console.log('Erreur demande inscription :', erreur);

        this.message =
          'Une erreur est survenue lors de l’envoi.';
      }

    });
  }
}