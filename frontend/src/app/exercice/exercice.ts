import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-exercice',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './exercice.html',
  styleUrl: './exercice.css',
})
export class Exercice {

  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);

  exercices: any[] = [];
  exercicesFiltres: any[] = [];
  accesExercicesRefuse: boolean = false;

  matiereSelectionnee = '';

  exerciceSelectionne: any = null;

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/exercices')
      .subscribe({
 next: (reponse) => {
  this.exercices = reponse;
  this.exercicesFiltres = reponse;

  this.http.get<any[]>('http://localhost:8080/favoris/mes-favoris')
  .subscribe({
    next: (favoris) => {

      this.exercices.forEach(exerciceItem => {

        exerciceItem.estFavori = favoris.some(
          favori =>
            favori.typeContenu === 'EXERCICE' &&
            favori.contenuId === exerciceItem.id
        );

      });

    },
    error: (erreur) => {
      console.log('Erreur chargement favoris exercices :', erreur);
    }
  });

  const idFavori = this.route.snapshot.queryParamMap.get('id');

  if (idFavori) {
    const exerciceTrouve = reponse.find(
      exercice => exercice.id === Number(idFavori)
    );

    if (exerciceTrouve) {
      this.ouvrirExercice(exerciceTrouve);
    }
  }

  console.log('Exercices reçus :', reponse);
},
  error: (erreur) => {
  console.log('Erreur exercices :', erreur);
  this.exercices = [];
  this.exercicesFiltres = [];
  this.accesExercicesRefuse = true;
    }
      });
  }

  filtrerParMatiere() {

    if (this.matiereSelectionnee === '') {
      this.exercicesFiltres = this.exercices;
      return;
    }

    this.exercicesFiltres = this.exercices.filter(
      exercice =>
        exercice.matiere?.toLowerCase() ===
        this.matiereSelectionnee.toLowerCase()
    );
  }

  ouvrirExercice(exercice: any) {
    this.exerciceSelectionne = exercice;
  }

  fermerExercice() {
    this.exerciceSelectionne = null;
  }

  ajouterFavori(exercice: any) {

  const donnees = {
    typeContenu: 'EXERCICE',
    contenuId: exercice.id
  };

  this.http.post(
    'http://localhost:8080/favoris',
    donnees
  ).subscribe({
    next: (reponse) => {
      console.log('Exercice ajouté aux favoris :', reponse);
      exercice.estFavori = true;
    },
    error: (erreur) => {
      console.log('Erreur favori :', erreur);
    }
  });
}

sidebarReduite = false;

toggleSidebar() {
  this.sidebarReduite = !this.sidebarReduite;
}
}