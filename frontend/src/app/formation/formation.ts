import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-formation',
  imports: [CommonModule],
  templateUrl: './formation.html',
  styleUrl: './formation.css',
})
export class Formation {
  private http = inject(HttpClient);

formations: any[] = [];

ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/formations')
    .subscribe({
      next: (reponse) => {
        this.formations = reponse;
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

}
