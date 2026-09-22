import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-profil',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './profil.html',
  styleUrl: './profil.css',
})

export class Profil {

  private router = inject(Router);
  private http = inject(HttpClient);

  profil: any = null;
  favoris: any[] = [];
  resultats: any[] = [];
  abonnementActif: any = null;

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

  this.http.get(
  'http://localhost:8080/abonnements/mon-abonnement-actif'
).subscribe({
  next: (reponse) => {
    this.abonnementActif = reponse;
    console.log('Abonnement actif :', reponse);
  },
  error: () => {
    this.abonnementActif = null;
    console.log('Aucun abonnement actif');
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

ouvrirFavori(favori: any) {

  if (favori.typeContenu === 'CONCOURS') {
    this.router.navigate(['/concours'], {
      queryParams: { id: favori.contenuId }
    });
  }

  if (favori.typeContenu === 'QCM') {
    this.router.navigate(['/qcm'], {
      queryParams: { id: favori.contenuId }
    });
  }
  if (favori.typeContenu === 'EXERCICE') {
  this.router.navigate(['/exercice'], {
    queryParams: { id: favori.contenuId }
  });
}

if (favori.typeContenu === 'ACTUALITE') {
  this.router.navigate(['/actualite'], {
    queryParams: { id: favori.contenuId }
  });
}

if (favori.typeContenu === 'FORMATION') {
  this.router.navigate(['/formation'], {
    queryParams: { id: favori.contenuId }
  });
}

}

sidebarReduite = false;

toggleSidebar() {
  this.sidebarReduite = !this.sidebarReduite;
}
}
