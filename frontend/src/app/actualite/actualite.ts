import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-actualite',
  imports: [CommonModule, RouterLink],
  templateUrl: './actualite.html',
  styleUrl: './actualite.css',
})
export class Actualite {

  estConnecte = !!localStorage.getItem('token');
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);

actualites: any[] = [];

ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/actualites')
    .subscribe({
next: (reponse) => {
  this.actualites = reponse;
if (this.estConnecte) {
  this.http.get<any[]>('http://localhost:8080/favoris/mes-favoris')
    .subscribe({
      next: (favoris) => {
        this.actualites.forEach(actualiteItem => {
          actualiteItem.estFavori = favoris.some(
            favori =>
              favori.typeContenu === 'ACTUALITE' &&
              favori.contenuId === actualiteItem.id
          );
        });
      },
      error: (erreur) => {
        console.log('Erreur chargement favoris :', erreur);
      }
    });
}

  const idFavori = this.route.snapshot.queryParamMap.get('id');

  if (idFavori) {
    const actualiteTrouvee = reponse.find(
      actualite => actualite.id === Number(idFavori)
    );

    if (actualiteTrouvee) {
      this.ouvrirActualite(actualiteTrouvee);
    }
  }

  console.log('Actualités reçues :', reponse);
},
      error: (erreur) => {
        console.log('Erreur actualités :', erreur);
      }
    });
}

actualiteSelectionnee: any = null;

ouvrirActualite(actualite: any) {
  this.actualiteSelectionnee = actualite;
}

fermerActualite() {
  this.actualiteSelectionnee = null;
}
ajouterFavori(actualite: any) {
  const donnees = {
    typeContenu: 'ACTUALITE',
    contenuId: actualite.id
  };

  this.http.post(
    'http://localhost:8080/favoris',
    donnees
  ).subscribe({
    next: (reponse) => {
      console.log('Actualité ajoutée aux favoris :', reponse);
      actualite.estFavori = true;
    },
    error: (erreur) => {
      console.log('Erreur favori :', erreur);
    }
  });
}

}
