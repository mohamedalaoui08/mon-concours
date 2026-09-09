import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-mot-de-passe-oublie',
  imports: [CommonModule, FormsModule],
  templateUrl: './mot-de-passe-oublie.html',
  styleUrl: './mot-de-passe-oublie.css',
})
export class MotDePasseOublie {

  email: string = '';
  message: string = '';
  erreur: string = '';

  constructor(private http: HttpClient) {}

  envoyerLien() {

    this.message = '';
    this.erreur = '';

    this.http.post(
      'http://localhost:8080/mot-de-passe-oublie',
      null,
      {
        params: {
          email: this.email
        },
        responseType: 'text'
      }
    ).subscribe({

      next: () => {
        this.message = 'Le lien de réinitialisation a bien été envoyé. Vérifiez votre boîte email.';
      },

      error: () => {
        this.erreur = 'Aucun compte trouvé avec cet email.';
      }

    });
  }
}