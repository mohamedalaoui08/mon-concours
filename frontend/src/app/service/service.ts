import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-service',
  imports: [CommonModule, RouterLink],
  templateUrl: './service.html',
  styleUrl: './service.css',
})
export class Service {
  estConnecte = !!localStorage.getItem('token');

  private http = inject(HttpClient);

  contenus: any[] = [];

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/contenus-service')
      .subscribe({
        next: (reponse) => {
          this.contenus = reponse;
        },
        error: (erreur) => {
          console.log('Erreur chargement page Service :', erreur);
        }
      });
  }
}