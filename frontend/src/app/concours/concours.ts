import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
@Component({
  selector: 'app-concours',
imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './concours.html',
  styleUrl: './concours.css',
})
export class Concours {
  private http = inject(HttpClient);
  concours: any[] = [];
  concoursFiltres: any[] = [];
  ecoleSelectionnee = '';
  private route = inject(ActivatedRoute);

  estConnecte = !!localStorage.getItem('token');

  ngOnInit() {
const url = this.estConnecte
  ? 'http://localhost:8080/concours'
  : 'http://localhost:8080/concours/public';

this.http.get<any[]>(url).subscribe({
next: (reponse) => {
  this.concours = reponse;
  this.concoursFiltres = reponse;
  if (this.estConnecte) {
  this.http.get<any[]>('http://localhost:8080/favoris/mes-favoris')
    .subscribe({
      next: (favoris) => {

        this.concours.forEach(concoursItem => {

          concoursItem.estFavori = favoris.some(
            favori =>
              favori.typeContenu === 'CONCOURS' &&
              favori.contenuId === concoursItem.id
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
    const concoursTrouve = reponse.find(
      concoursItem => concoursItem.id === Number(idFavori)
    );

    if (concoursTrouve) {
      console.log('Concours favori ouvert :', concoursTrouve);
    }
  }

  console.log('Concours reçus :', reponse);
},
        error: (erreur) => {
          console.log('Erreur concours :', erreur);
        }
      });
  }

  telechargerPdf(id: number) {
    this.http.get(
      `http://localhost:8080/concours/${id}/pdf`,
      { responseType: 'blob' }
    ).subscribe({
      next: (pdf) => {
        const url = window.URL.createObjectURL(pdf);
        const lien = document.createElement('a');
  
        lien.href = url;
        lien.download = 'concours.pdf';
        lien.click();
  
        window.URL.revokeObjectURL(url);
      },
      error: (erreur) => {
        console.log('Erreur téléchargement PDF :', erreur);
      }
    });
  }

  ajouterFavori(concours: any) {

    const donnees = {
      typeContenu: 'CONCOURS',
      contenuId: concours.id
    };
  
    this.http.post(
      'http://localhost:8080/favoris',
      donnees
    ).subscribe({
      next: (reponse) => {
        console.log('Favori ajouté :', reponse);
        concours.estFavori = true;
      },
      error: (erreur) => {
        console.log('Erreur favori :', erreur);
      }
    });
  }

filtrerParEcole() {

  if (this.ecoleSelectionnee === '') {
    this.concoursFiltres = this.concours;
    return;
  }

  this.concoursFiltres = this.concours.filter(
    concoursItem =>
      concoursItem.ecole?.nom === this.ecoleSelectionnee
  );
}

}
