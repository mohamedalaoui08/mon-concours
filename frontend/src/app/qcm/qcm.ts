import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
@Component({
  selector: 'app-qcm',
  imports: [CommonModule, RouterLink],
  templateUrl: './qcm.html',
  styleUrl: './qcm.css',
})
export class Qcm {

  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);
  qcms: any[] = [];
  qcmSelectionne: any = null;
  choixSelectionnes: number[] = [];
  resultatQcm: any = null;
  accesQcmRefuse: boolean = false;
  meilleursScores: { [qcmId: number]: number | undefined } = {};
  

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/qcms')
      .subscribe({
next: (reponse) => {
  this.qcms = reponse;

  this.qcms.forEach(qcm => {
  this.http.get<number>(
    `http://localhost:8080/resultats/meilleur-score/${qcm.id}`
  ).subscribe({
    next: (score) => {
      this.meilleursScores[qcm.id] = score;
    },
    error: (erreur) => {
      console.log('Erreur meilleur score :', erreur);
    }
  });
});

  const idFavori = this.route.snapshot.queryParamMap.get('id');

  if (idFavori) {
    const qcmTrouve = reponse.find(
      qcm => qcm.id === Number(idFavori)
    );

    if (qcmTrouve) {
      this.ouvrirQcm(qcmTrouve);
    }
  }

  console.log('QCM reçus :', reponse);
},
     error: (erreur) => {
  console.log('Erreur QCM :', erreur);
  this.qcms = [];
  this.accesQcmRefuse = true;
}
      });
  }
ouvrirQcm(qcm: any) {
  this.http.get<any>(`http://localhost:8080/qcms/${qcm.id}`)
    .subscribe({
      next: (reponse) => {
        this.qcmSelectionne = reponse;
        console.log('QCM complet ouvert :', reponse);
      },
      error: (erreur) => {
        console.log('Erreur ouverture QCM :', erreur);
      }
    });
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

fermerQcm() {
  this.qcmSelectionne = null;
  this.choixSelectionnes = [];
  this.resultatQcm = null;
}

sidebarReduite = false;

toggleSidebar() {
  this.sidebarReduite = !this.sidebarReduite;
}
}