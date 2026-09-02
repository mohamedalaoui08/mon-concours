import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-connexion',
  imports: [FormsModule],
  templateUrl: './connexion.html',
  styleUrl: './connexion.css',
})
export class Connexion {

  private http = inject(HttpClient);
  private router = inject(Router);

  email: string = '';
  motDePasse: string = '';

  seConnecter() {
    const donnees = {
      email: this.email,
      motDePasse: this.motDePasse
    };

    this.http.post(
      'http://localhost:8080/login',
      donnees,
      { responseType: 'text' }
    ).subscribe({
      next: (reponse) => {
        console.log('Réponse backend :', reponse);
        localStorage.setItem('token', reponse);
        this.router.navigate(['/']);
      },
      error: (erreur) => {
        console.log('Erreur :', erreur);
      }
    });
    
  }
}
