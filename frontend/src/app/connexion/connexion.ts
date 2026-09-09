import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-connexion',
imports: [CommonModule, FormsModule, RouterLink],
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
        localStorage.setItem('email', this.email);
        const payload = JSON.parse(
  atob(reponse.split('.')[1])
);

const role = payload.role;
console.log('ROLE DU JWT :', role);

if (role === 'ADMIN') {
  this.router.navigate(['/admin']);
} else if (role === 'ETUDIANT') {
  this.router.navigate(['/dashboard']);
}
      },
      error: (erreur) => {
        console.log('Erreur :', erreur);
      }
    });
    
  }
}
