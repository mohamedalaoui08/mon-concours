import { Component, OnInit, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent implements OnInit {

  private http = inject(HttpClient);

  messageBackend: string = 'Chargement en cours...';

  ngOnInit() {
    this.http.get('http://localhost:8080/ping', {
      responseType: 'text'
    }).subscribe({
      next: (data) => {
        console.log('SUCCÈS :', data);
        this.messageBackend = data;
      },
      error: (err) => {
        console.log('ERREUR :', err);
        this.messageBackend = 'Erreur';
      }
    });
  }
}