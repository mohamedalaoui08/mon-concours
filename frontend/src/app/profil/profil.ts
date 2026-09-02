import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-profil',
  imports: [CommonModule, FormsModule],
  templateUrl: './profil.html',
  styleUrl: './profil.css',
})

export class Profil {

  private router = inject(Router);
  private http = inject(HttpClient);

  profil: any = null;
  favoris: any[] = [];
  resultats: any[] = [];

  ngOnInit() {

    this.http.get('http://localhost:8080/etudiants/mon-profil')
      .subscribe({
        next: (reponse) => {
          this.profil = reponse;
          console.log('Profil reçu :', reponse);
        },
        error: (erreur) => {
          console.log('Erreur profil :', erreur);
        }
      });
  
    // JUSTE ICI 👇
    this.http.get<any[]>('http://localhost:8080/favoris/mes-favoris')
      .subscribe({
        next: (reponse) => {
          this.favoris = reponse;
          console.log('Mes favoris :', reponse);
        },
        error: (erreur) => {
          console.log('Erreur favoris :', erreur);
        }
      });

    this.http.get<any[]>('http://localhost:8080/resultats/mes-resultats')
    .subscribe({
    next: (reponse) => {
      this.resultats = reponse;
      console.log('Mes résultats :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur résultats :', erreur);
    }
  });
  
  }

  modifierProfil() {
    this.http.put(
      'http://localhost:8080/etudiants/mon-profil',
      this.profil
    ).subscribe({
      next: (reponse) => {
        console.log('Profil modifié :', reponse);
      },
      error: (erreur) => {
        console.log('Erreur modification :', erreur);
      }
    });
  }
  
  seDeconnecter() {
    localStorage.removeItem('token');
    this.router.navigate(['/connexion']);
  }

  supprimerFavori(id: number) {

    this.http.delete(
      `http://localhost:8080/favoris/${id}`
    ).subscribe({
      next: () => {
        console.log('Favori supprimé');
  
        this.favoris = this.favoris.filter(
          favori => favori.id !== id
        );
      },
      error: (erreur) => {
        console.log('Erreur suppression favori :', erreur);
      }
    });
  
  }

}
