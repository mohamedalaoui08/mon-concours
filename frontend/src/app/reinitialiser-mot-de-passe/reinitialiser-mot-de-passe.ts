import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-reinitialiser-mot-de-passe',
  imports: [CommonModule, FormsModule],
  templateUrl: './reinitialiser-mot-de-passe.html',
  styleUrl: './reinitialiser-mot-de-passe.css',
})
export class ReinitialiserMotDePasse {

  token: string = '';
  nouveauMotDePasse: string = '';
  confirmationMotDePasse: string = '';

  message: string = '';
  erreur: string = '';

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.token = this.route.snapshot.queryParamMap.get('token') || '';
  }

  reinitialiser() {

    this.message = '';
    this.erreur = '';

    if (this.nouveauMotDePasse !== this.confirmationMotDePasse) {
      this.erreur = 'Les mots de passe ne correspondent pas.';
      return;
    }

    this.http.post(
      'http://localhost:8080/reinitialiser-mot-de-passe',
      null,
      {
        params: {
          token: this.token,
          nouveauMotDePasse: this.nouveauMotDePasse
        },
        responseType: 'text'
      }
    ).subscribe({

      next: (reponse) => {
        this.message = reponse;

        setTimeout(() => {
          this.router.navigate(['/connexion']);
        }, 2000);
      },

      error: () => {
        this.erreur = 'Le lien est invalide ou expiré.';
      }

    });
  }
}