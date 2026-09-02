import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-qcm',
  imports: [CommonModule],
  templateUrl: './qcm.html',
  styleUrl: './qcm.css',
})
export class Qcm {

  private http = inject(HttpClient);

  qcms: any[] = [];
  qcmSelectionne: any = null;
  choixSelectionnes: number[] = [];
  resultatQcm: any = null;

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/qcms')
      .subscribe({
        next: (reponse) => {
          this.qcms = reponse;
          console.log('QCM reçus :', reponse);
        },
        error: (erreur) => {
          console.log('Erreur QCM :', erreur);
        }
      });
  }
  ouvrirQcm(qcm: any) {
    this.qcmSelectionne = qcm;
   
  }

  selectionnerChoix(questionId: number, choixId: number) {
    this.choixSelectionnes[questionId] = choixId;
    console.log('Choix sélectionnés :', this.choixSelectionnes);
  }

  validerQcm() {
    const choixIds = this.choixSelectionnes.filter(
      choixId => choixId !== undefined
    );
  
    console.log('IDs à envoyer :', choixIds);
 

  this.http.post(
    `http://localhost:8080/qcms/${this.qcmSelectionne.id}/passer`,
    { choixIds: choixIds }
  ).subscribe({
    next: (reponse) => {
      console.log('Résultat du QCM :', reponse);
      this.resultatQcm = reponse;
    },
    error: (erreur) => {
      console.log('Erreur validation QCM :', erreur);
    }
  });
}
}
