import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-abonnement',
  imports: [CommonModule],
  templateUrl: './abonnement.html',
  styleUrl: './abonnement.css',
})
export class Abonnement {

  private http = inject(HttpClient);

offres: any[] = [];
messageAbonnement: string = '';

ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/offres-abonnement')
    .subscribe({
      next: (reponse) => {
        this.offres = reponse;
        console.log('Offres reçues :', reponse);
      },
      error: (erreur) => {
        console.log('Erreur offres :', erreur);
      }
    });
}

souscrire(offre: any) {
  console.log('Offre envoyée :', offre);
  console.log('ID offre :', offre.id);
  const donnees = {
    offreId: offre.id
  };

  this.http.post(
    'http://localhost:8080/abonnements/souscrire',
    donnees
  ).subscribe({
    next: (reponse) => {
      console.log('Abonnement créé :', reponse);
      this.messageAbonnement = 'Abonnement créé avec succès';
    },
    error: (erreur) => {
      console.log('Erreur abonnement :', erreur);
      console.log('Message backend :', erreur.error);
      this.messageAbonnement = erreur.error;
    }
  });

}

}
