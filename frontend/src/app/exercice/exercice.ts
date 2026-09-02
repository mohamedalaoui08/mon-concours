import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-exercice',
  imports: [CommonModule],
  templateUrl: './exercice.html',
  styleUrl: './exercice.css',
})
export class Exercice {
  private http = inject(HttpClient);

exercices: any[] = [];

ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/exercices')
    .subscribe({
      next: (reponse) => {
        this.exercices = reponse;
        console.log('Exercices reçus :', reponse);
      },
      error: (erreur) => {
        console.log('Erreur exercices :', erreur);
      }
    });
}

exerciceSelectionne: any = null;

ouvrirExercice(exercice: any) {
  this.exerciceSelectionne = exercice;
}

}
