import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actualite',
  imports: [CommonModule],
  templateUrl: './actualite.html',
  styleUrl: './actualite.css',
})
export class Actualite {
  private http = inject(HttpClient);

actualites: any[] = [];

ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/actualites')
    .subscribe({
      next: (reponse) => {
        this.actualites = reponse;
        console.log('Actualités reçues :', reponse);
      },
      error: (erreur) => {
        console.log('Erreur actualités :', erreur);
      }
    });
}
}
