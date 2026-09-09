import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-abonnement',
  imports: [CommonModule, RouterLink],
  templateUrl: './abonnement.html',
  styleUrl: './abonnement.css',
})
export class Abonnement {

  private http = inject(HttpClient);

offres: any[] = [];
abonnementActif: any = null;
messageAbonnement: string = '';
estConnecte = !!localStorage.getItem('token');

ngOnInit() {

  if (this.estConnecte) {

  this.http.get(
    'http://localhost:8080/abonnements/mon-abonnement-actif'
  ).subscribe({
    next: (reponse) => {
      this.abonnementActif = reponse;
      console.log('Abonnement actif :', reponse);
    },
    error: () => {
      this.abonnementActif = null;
    }
  });

}

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
next: (reponse: any) => {
  console.log('Abonnement créé :', reponse);

  this.abonnementActif = reponse;

  this.messageAbonnement = 'Abonnement créé avec succès';
},
    error: (erreur) => {
      console.log('Erreur abonnement :', erreur);
      console.log('Message backend :', erreur.error);
      this.messageAbonnement = erreur.error;
    }
  });

}

resilierAbonnement() {

  this.http.put(
    'http://localhost:8080/abonnements/resilier',
    null
  ).subscribe({
next: () => {
  this.abonnementActif = null;
  this.messageAbonnement = 'Abonnement résilié avec succès';
},
    error: (erreur) => {
      console.log('Erreur résiliation :', erreur);
      this.messageAbonnement = erreur.error;
    }
  });

}

changerAbonnement(offre: any) {

  const donnees = {
    offreId: offre.id
  };

  this.http.put(
    'http://localhost:8080/abonnements/changer',
    donnees
  ).subscribe({
    next: (reponse: any) => {

      this.abonnementActif = reponse;

      this.messageAbonnement =
        'Abonnement changé avec succès';
    },

    error: (erreur) => {
      console.log('Erreur changement abonnement :', erreur);
      this.messageAbonnement = erreur.error;
    }
  });

}
}
