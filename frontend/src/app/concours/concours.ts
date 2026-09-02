import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-concours',
  imports: [CommonModule],
  templateUrl: './concours.html',
  styleUrl: './concours.css',
})
export class Concours {
  private http = inject(HttpClient);
  concours: any[] = [];

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/concours')
      .subscribe({
        next: (reponse) => {
          this.concours = reponse;
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
      },
      error: (erreur) => {
        console.log('Erreur favori :', erreur);
      }
    });
  }



}
