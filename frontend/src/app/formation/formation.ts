import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-formation',
imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './formation.html',
  styleUrl: './formation.css',
})
export class Formation {
  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  formations: any[] = [];
  formationsFiltres: any[] = [];
  



matiereSelectionnee = '';
typeSelectionne = '';

ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/formations')
    .subscribe({
      next: (reponse) => {
        this.formations = reponse;
        this.formationsFiltres = reponse;
        const idFormation = this.route.snapshot.queryParamMap.get('id');

if (idFormation) {
  const formationTrouvee = this.formations.find(
    (formation: any) => formation.id === Number(idFormation)
  );

  if (formationTrouvee) {
    this.ouvrirFormation(formationTrouvee);
  }
}
        this.http.get<any[]>('http://localhost:8080/favoris/mes-favoris')
  .subscribe({
    next: (favoris) => {
      this.formations.forEach(formationItem => {
        formationItem.estFavori = favoris.some(
          favori =>
            favori.typeContenu === 'FORMATION' &&
            favori.contenuId === formationItem.id
        );
      });
    },
    error: (erreur) => {
      console.log('Erreur chargement favoris :', erreur);
    }
  });
        console.log('Formations reçues :', reponse);
      },
      error: (erreur) => {
        console.log('Erreur formations :', erreur);
      }
    });
}

formationSelectionnee: any = null;

ouvrirFormation(formation: any) {
  this.formationSelectionnee = formation;
}

filtrerFormations() {
  this.formationsFiltres = this.formations.filter((formation: any) => {

    const correspondMatiere =
      this.matiereSelectionnee === '' ||
      formation.matiere === this.matiereSelectionnee;

    const correspondType =
      this.typeSelectionne === '' ||
      formation.type === this.typeSelectionne;

    return correspondMatiere && correspondType;
  });
}

ajouterFavori(formation: any) {
  const donnees = {
    typeContenu: 'FORMATION',
    contenuId: formation.id
  };

  this.http.post(
    'http://localhost:8080/favoris',
    donnees
  ).subscribe({
    next: (reponse) => {
      console.log('Formation ajoutée aux favoris :', reponse);
      formation.estFavori = true;
    },
    error: (erreur) => {
      console.log('Erreur favori :', erreur);
    }
  });
}

}
