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

  /* =========================
     DONNÉES DU FORMULAIRE
     ========================= */

  demande = {
    nom: '',
    prenom: '',
    email: '',
    dateNaissance: '',
    niveau: ''
  };


  /* =========================
     MESSAGE APRÈS ENVOI
     ========================= */

  message = '';


  /* =========================
     ENVOYER LA DEMANDE
     ========================= */

  envoyerDemande() {

    this.http.post(
      'http://localhost:8080/demandes-inscription',
      this.demande
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
      },

      error: (erreur) => {
        console.log('Erreur demande inscription :', erreur);

        this.message =
          'Une erreur est survenue lors de l’envoi.';
      }

    });
  }
}