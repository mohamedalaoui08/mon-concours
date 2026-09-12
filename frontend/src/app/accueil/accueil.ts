import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-accueil',
  imports: [CommonModule, RouterLink],
  templateUrl: './accueil.html',
  styleUrl: './accueil.css',
})
export class Accueil {

  private http = inject(HttpClient);

  offres: any[] = [];

  ngOnInit() {
this.http.get<any[]>('http://localhost:8080/offres-abonnement/actives')
      .subscribe({
      next: (reponse) => {
  this.offres = reponse
    .sort((a, b) => b.id - a.id)
    .slice(0, 3);
},
        error: (erreur) => {
          console.log('Erreur chargement des offres :', erreur);
        }
      });
  }
}