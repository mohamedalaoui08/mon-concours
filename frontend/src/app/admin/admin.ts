import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin',
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin {
  private http = inject(HttpClient);

concours: any[] = [];
concoursAModifier: any = null;
ecoles: any[] = [];
pdfSelectionne: File | null = null;
qcms: any[] = [];
qcmAModifier: any = null;
questions: any[] = [];
questionAModifier: any = null;
choix: any[] = [];
choixAModifier: any = null;
exercices: any[] = [];
exerciceAModifier: any = null;
formations: any[] = [];

formationAModifier: any = null;
actualites: any[] = [];

actualiteAModifier: any = null;

ecoleAModifier: any = null;

offresAbonnement: any[] = [];

offreAbonnementAModifier: any = null;

etudiants: any[] = [];
etudiantAModifier: any = null;
admins: any[] = [];
adminAModifier: any = null;
demandesInscription: any[] = [];

abonnements: any[] = [];

nouvelleOffreAbonnement: any = {
  nom: '',
  description: '',
  prix: 0,
  dureeJours: 0
};

nouvelleEcole: any = {
  nom: '',
  description: ''
};

nouvelleActualite: any = {
  titre: '',
  contenu: '',
  datePublication: '',
  ecole: null
};

nouvelleFormation: any = {
  titre: '',
  description: '',
  contenu: '',
  duree: null,
  niveau: '',
  datePublication: ''
};

nouveauConcours: any = {
  nom: '',
  date: '',
  description: '',
  fichierPdf: ''
};

nouveauQcm: any = {
  titre: '',
  duree: null,
  niveau: ''
};

nouvelleQuestion: any = {
  enonce: '',
  qcm: null
};

nouveauChoix: any = {
  texte: '',
  estCorrecte: false,
  question: null
};

nouvelExercice: any = {
  titre: '',
  enonce: '',
  matiere: ''
};


ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/concours').subscribe({
    next: (reponse) => {
      this.concours = reponse;
      console.log('Concours admin reçus :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur concours admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/ecoles').subscribe({
    next: (reponse) => {
      this.ecoles = reponse;
      console.log('Écoles reçues :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur écoles :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/qcms').subscribe({
    next: (reponse) => {
      this.qcms = reponse;
      console.log('QCM admin reçus :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur QCM admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/questions').subscribe({
    next: (reponse) => {
      this.questions = reponse;
      console.log('Questions admin reçues :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur questions admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/choix').subscribe({
    next: (reponse) => {
      this.choix = reponse;
      console.log('Choix admin reçus :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur récupération choix :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/exercices').subscribe({
    next: (reponse) => {
      this.exercices = reponse;
      console.log('Exercices admin reçus :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur exercices admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/formations').subscribe({
    next: (reponse) => {
      this.formations = reponse;
      console.log('Formations admin reçues :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur formations admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/actualites').subscribe({
    next: (reponse) => {
      this.actualites = reponse;
      console.log('Actualités admin reçues :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur actualités admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/offres-abonnement').subscribe({
    next: (reponse) => {
      this.offresAbonnement = reponse;
      console.log('Offres abonnement admin reçues :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur offres abonnement admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/etudiants').subscribe({
    next: (reponse) => {
      this.etudiants = reponse;
      console.log('Étudiants admin reçus :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur étudiants admin :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/admins').subscribe({
    next: (reponse) => {
      this.admins = reponse;
      console.log('Admins reçus :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur récupération admins :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/demandes-inscription').subscribe({
    next: (reponse) => {
      this.demandesInscription = reponse;
      console.log('Demandes inscription reçues :', reponse);
    },
    error: (erreur) => {
      console.log('Erreur demandes inscription :', erreur);
    }
  });

  this.http.get<any[]>('http://localhost:8080/abonnements').subscribe({
  next: (reponse) => {
    this.abonnements = reponse;
    console.log('Abonnements admin reçus :', reponse);
  },
  error: (erreur) => {
    console.log('Erreur abonnements admin :', erreur);
  }
});

}

supprimerConcours(id: number) {
  this.http.delete(
    `http://localhost:8080/concours/${id}`
  ).subscribe({
    next: () => {
      console.log('Concours supprimé');
      this.concours = this.concours.filter(
        concoursItem => concoursItem.id !== id
      );
    },
    error: (erreur) => {
      console.log('Erreur suppression concours :', erreur);
    }
  });
}

ajouterConcours() {
  this.http.post(
    'http://localhost:8080/concours',
    this.nouveauConcours
  ).subscribe({
    next: (reponse: any) => {
      console.log('Concours ajouté :', reponse);
      this.concours.push(reponse);

      if (this.pdfSelectionne) {
        const formData = new FormData();
        formData.append('fichier', this.pdfSelectionne);

        this.http.post(
          `http://localhost:8080/concours/${reponse.id}/pdf`,
          formData
        ).subscribe({
          next: (concoursAvecPdf: any) => {
            console.log('PDF envoyé :', concoursAvecPdf);
          },
          error: (erreur) => {
            console.log('Erreur envoi PDF :', erreur);
          }
        });
      }
    },
    error: (erreur) => {
      console.log('Erreur ajout concours :', erreur);
    }
  });
}

selectionnerConcours(concours: any) {
  this.concoursAModifier = { ...concours };

  console.log('Concours à modifier :', this.concoursAModifier);
}

modifierConcours() {

  this.http.put(
    `http://localhost:8080/concours/${this.concoursAModifier.id}`,
    this.concoursAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Concours modifié :', reponse);

      // AJOUTE LE CODE ICI
      if (this.pdfSelectionne) {

        const formData = new FormData();
        formData.append('fichier', this.pdfSelectionne);

        this.http.post(
          `http://localhost:8080/concours/${reponse.id}/pdf`,
          formData
        ).subscribe({
          next: (concoursAvecPdf: any) => {
            console.log('Nouveau PDF envoyé :', concoursAvecPdf);
          },
          error: (erreur) => {
            console.log('Erreur modification PDF :', erreur);
          }
        });

      }

      const index = this.concours.findIndex(
        concoursItem => concoursItem.id === reponse.id
      );

      if (index !== -1) {
        this.concours[index] = reponse;
      }

      this.concoursAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification concours :', erreur);
    }

  });

}

selectionnerPdf(event: Event) {
  const input = event.target as HTMLInputElement;

  if (input.files && input.files.length > 0) {
    this.pdfSelectionne = input.files[0];
    console.log('PDF sélectionné :', this.pdfSelectionne);
  }
}

supprimerQcm(id: number) {
  this.http.delete(
    `http://localhost:8080/qcms/${id}`
  ).subscribe({
    next: () => {
      console.log('QCM supprimé');

      this.qcms = this.qcms.filter(
        qcmItem => qcmItem.id !== id
      );
    },
    error: (erreur) => {
      console.log('Erreur suppression QCM :', erreur);
    }
  });
} 

ajouterQcm() {

  this.http.post(
    'http://localhost:8080/qcms',
    this.nouveauQcm
  ).subscribe({

    next: (reponse: any) => {
      console.log('QCM ajouté :', reponse);

      this.qcms.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout QCM :', erreur);
    }

  });

}

selectionnerQcm(qcm: any) {
  this.qcmAModifier = { ...qcm };

  console.log('QCM à modifier :', this.qcmAModifier);
}

modifierQcm() {

  this.http.put(
    `http://localhost:8080/qcms/${this.qcmAModifier.id}`,
    this.qcmAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('QCM modifié :', reponse);

      const index = this.qcms.findIndex(
        qcmItem => qcmItem.id === reponse.id
      );

      if (index !== -1) {
        this.qcms[index] = reponse;
      }

      this.qcmAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification QCM :', erreur);
    }

  });

}

ajouterQuestion() {

  this.http.post(
    'http://localhost:8080/questions',
    this.nouvelleQuestion
  ).subscribe({
    next: (reponse: any) => {
      console.log('Question ajoutée :', reponse);
      this.questions.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout question :', erreur);
    }

  });

}

selectionnerQuestion(question: any) {
  this.questionAModifier = { ...question };

  console.log('Question à modifier :', this.questionAModifier);
}

supprimerQuestion(id: number) {
  this.http.delete(
    `http://localhost:8080/questions/${id}`
  ).subscribe({
    next: () => {
      console.log('Question supprimée');

      this.questions = this.questions.filter(
        questionItem => questionItem.id !== id
      );
    },
    error: (erreur) => {
      console.log('Erreur suppression question :', erreur);
    }
  });
}

modifierQuestion() {

  this.http.put(
    `http://localhost:8080/questions/${this.questionAModifier.id}`,
    this.questionAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Question modifiée :', reponse);

      const index = this.questions.findIndex(
        questionItem => questionItem.id === reponse.id
      );

      if (index !== -1) {
        this.questions[index] = reponse;
      }

      this.questionAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification question :', erreur);
    }

  });

}

ajouterChoix() {

  this.http.post(
    'http://localhost:8080/choix',
    this.nouveauChoix
  ).subscribe({

    next: (reponse: any) => {
      console.log('Choix ajouté :', reponse);
      this.choix.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout choix :', erreur);
    }

  });

}

selectionnerChoix(choixItem: any) {
  this.choixAModifier = { ...choixItem };

  console.log('Choix à modifier :', this.choixAModifier);
}

supprimerChoix(id: number) {

  this.http.delete(
    `http://localhost:8080/choix/${id}`
  ).subscribe({

    next: () => {
      console.log('Choix supprimé');

      this.choix = this.choix.filter(
        choixItem => choixItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression choix :', erreur);
    }

  });
}

modifierChoix() {

  this.http.put(
    `http://localhost:8080/choix/${this.choixAModifier.id}`,
    this.choixAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Choix modifié :', reponse);

      const index = this.choix.findIndex(
        choixItem => choixItem.id === reponse.id
      );

      if (index !== -1) {
        this.choix[index] = reponse;
      }

      this.choixAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification choix :', erreur);
    }

  });

}

ajouterExercice() {

  this.http.post(
    'http://localhost:8080/exercices',
    this.nouvelExercice
  ).subscribe({

    next: (reponse: any) => {
      console.log('Exercice ajouté :', reponse);

      this.exercices.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout exercice :', erreur);
    }

  });

}

selectionnerExercice(exercice: any) {
  this.exerciceAModifier = { ...exercice };

  console.log('Exercice à modifier :', this.exerciceAModifier);
}

supprimerExercice(id: number) {

  this.http.delete(
    `http://localhost:8080/exercices/${id}`
  ).subscribe({

    next: () => {
      console.log('Exercice supprimé');

      this.exercices = this.exercices.filter(
        exerciceItem => exerciceItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression exercice :', erreur);
    }

  });
}

modifierExercice() {

  this.http.put(
    `http://localhost:8080/exercices/${this.exerciceAModifier.id}`,
    this.exerciceAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Exercice modifié :', reponse);

      const index = this.exercices.findIndex(
        exerciceItem => exerciceItem.id === reponse.id
      );

      if (index !== -1) {
        this.exercices[index] = reponse;
      }

      this.exerciceAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification exercice :', erreur);
    }

  });

}

ajouterFormation() {

  this.http.post(
    'http://localhost:8080/formations',
    this.nouvelleFormation
  ).subscribe({

    next: (reponse: any) => {
      console.log('Formation ajoutée :', reponse);

      this.formations.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout formation :', erreur);
    }

  });

}

selectionnerFormation(formation: any) {
  this.formationAModifier = { ...formation };

  console.log('Formation à modifier :', this.formationAModifier);
}

supprimerFormation(id: number) {

  this.http.delete(
    `http://localhost:8080/formations/${id}`
  ).subscribe({

    next: () => {
      console.log('Formation supprimée');

      this.formations = this.formations.filter(
        formationItem => formationItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression formation :', erreur);
    }

  });
}

modifierFormation() {

  this.http.put(
    `http://localhost:8080/formations/${this.formationAModifier.id}`,
    this.formationAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Formation modifiée :', reponse);

      const index = this.formations.findIndex(
        formationItem => formationItem.id === reponse.id
      );

      if (index !== -1) {
        this.formations[index] = reponse;
      }

      this.formationAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification formation :', erreur);
    }

  });
}

ajouterActualite() {

  this.http.post(
    'http://localhost:8080/actualites',
    this.nouvelleActualite
  ).subscribe({

    next: (reponse: any) => {
      console.log('Actualité ajoutée :', reponse);

      this.actualites.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout actualité :', erreur);
    }

  });

}

selectionnerActualite(actualite: any) {
  this.actualiteAModifier = { ...actualite };

  console.log('Actualité à modifier :', this.actualiteAModifier);
}

supprimerActualite(id: number) {

  this.http.delete(
    `http://localhost:8080/actualites/${id}`
  ).subscribe({

    next: () => {
      console.log('Actualité supprimée');

      this.actualites = this.actualites.filter(
        actualiteItem => actualiteItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression actualité :', erreur);
    }

  });
}

modifierActualite() {

  this.http.put(
    `http://localhost:8080/actualites/${this.actualiteAModifier.id}`,
    this.actualiteAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Actualité modifiée :', reponse);

      const index = this.actualites.findIndex(
        actualiteItem => actualiteItem.id === reponse.id
      );

      if (index !== -1) {
        this.actualites[index] = reponse;
      }

      this.actualiteAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification actualité :', erreur);
    }

  });
}

ajouterEcole() {

  this.http.post(
    'http://localhost:8080/ecoles',
    this.nouvelleEcole
  ).subscribe({

    next: (reponse: any) => {
      console.log('École ajoutée :', reponse);

      this.ecoles.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout école :', erreur);
    }

  });
}

selectionnerEcole(ecole: any) {
  this.ecoleAModifier = { ...ecole };

  console.log('École à modifier :', this.ecoleAModifier);
}

supprimerEcole(id: number) {

  this.http.delete(
    `http://localhost:8080/ecoles/${id}`
  ).subscribe({

    next: () => {
      console.log('École supprimée');

      this.ecoles = this.ecoles.filter(
        ecoleItem => ecoleItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression école :', erreur);
    }

  });
}

modifierEcole() {

  this.http.put(
    `http://localhost:8080/ecoles/${this.ecoleAModifier.id}`,
    this.ecoleAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('École modifiée :', reponse);

      const index = this.ecoles.findIndex(
        ecoleItem => ecoleItem.id === reponse.id
      );

      if (index !== -1) {
        this.ecoles[index] = reponse;
      }

      this.ecoleAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification école :', erreur);
    }

  });
}

ajouterOffreAbonnement() {

  this.http.post(
    'http://localhost:8080/offres-abonnement',
    this.nouvelleOffreAbonnement
  ).subscribe({

    next: (reponse: any) => {
      console.log('Offre ajoutée :', reponse);

      this.offresAbonnement.push(reponse);
    },

    error: (erreur) => {
      console.log('Erreur ajout offre :', erreur);
    }

  });
}

selectionnerOffreAbonnement(offre: any) {
  this.offreAbonnementAModifier = { ...offre };

  console.log('Offre à modifier :', this.offreAbonnementAModifier);
}

supprimerOffreAbonnement(id: number) {

  this.http.delete(
    `http://localhost:8080/offres-abonnement/${id}`
  ).subscribe({

    next: () => {
      console.log('Offre supprimée');

      this.offresAbonnement = this.offresAbonnement.filter(
        offreItem => offreItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression offre :', erreur);
    }

  });
}

modifierOffreAbonnement() {

  this.http.put(
    `http://localhost:8080/offres-abonnement/${this.offreAbonnementAModifier.id}`,
    this.offreAbonnementAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Offre modifiée :', reponse);

      const index = this.offresAbonnement.findIndex(
        offreItem => offreItem.id === reponse.id
      );

      if (index !== -1) {
        this.offresAbonnement[index] = reponse;
      }

      this.offreAbonnementAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification offre :', erreur);
    }

  });
}

selectionnerEtudiant(etudiant: any) {
  this.etudiantAModifier = { ...etudiant };

  console.log('Étudiant à modifier :', this.etudiantAModifier);
}

supprimerEtudiant(id: number) {

  this.http.delete(
    `http://localhost:8080/etudiants/${id}`
  ).subscribe({

    next: () => {
      console.log('Étudiant supprimé');

      this.etudiants = this.etudiants.filter(
        etudiantItem => etudiantItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression étudiant :', erreur);
    }

  });
}

modifierEtudiant() {

  this.http.put(
    `http://localhost:8080/etudiants/${this.etudiantAModifier.id}`,
    this.etudiantAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Étudiant modifié :', reponse);

      const index = this.etudiants.findIndex(
        etudiantItem => etudiantItem.id === reponse.id
      );

      if (index !== -1) {
        this.etudiants[index] = reponse;
      }

      this.etudiantAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification étudiant :', erreur);
    }

  });
}

selectionnerAdmin(admin: any) {
  this.adminAModifier = { ...admin };

  console.log('Admin à modifier :', this.adminAModifier);
}

supprimerAdmin(id: number) {

  this.http.delete(
    `http://localhost:8080/admins/${id}`
  ).subscribe({

    next: () => {
      console.log('Admin supprimé');

      this.admins = this.admins.filter(
        adminItem => adminItem.id !== id
      );
    },

    error: (erreur) => {
      console.log('Erreur suppression admin :', erreur);
    }

  });
}

modifierAdmin() {

  this.http.put(
    `http://localhost:8080/admins/${this.adminAModifier.id}`,
    this.adminAModifier
  ).subscribe({

    next: (reponse: any) => {
      console.log('Admin modifié :', reponse);

      const index = this.admins.findIndex(
        adminItem => adminItem.id === reponse.id
      );

      if (index !== -1) {
        this.admins[index] = reponse;
      }

      this.adminAModifier = null;
    },

    error: (erreur) => {
      console.log('Erreur modification admin :', erreur);
    }

  });
}

accepterDemande(id: number) {

  this.http.put(
    `http://localhost:8080/demandes-inscription/${id}/accepter`,
    {}
  ).subscribe({

    next: (reponse: any) => {
      console.log('Demande acceptée :', reponse);

      const index = this.demandesInscription.findIndex(
        demande => demande.id === reponse.id
      );

      if (index !== -1) {
        this.demandesInscription[index] = reponse;
      }
    },

    error: (erreur) => {
      console.log('Erreur acceptation demande :', erreur);
    }

  });
}

refuserDemande(id: number) {

  this.http.put(
    `http://localhost:8080/demandes-inscription/${id}/refuser`,
    {}
  ).subscribe({

    next: (reponse: any) => {
      console.log('Demande refusée :', reponse);

      const index = this.demandesInscription.findIndex(
        demande => demande.id === reponse.id
      );

      if (index !== -1) {
        this.demandesInscription[index] = reponse;
      }
    },

    error: (erreur) => {
      console.log('Erreur refus demande :', erreur);
    }

  });
}

supprimerAbonnement(id: number) {
  this.http.delete(
    `http://localhost:8080/abonnements/${id}`
  ).subscribe({
    next: () => {
      this.abonnements = this.abonnements.filter(
        abonnement => abonnement.id !== id
      );

      console.log('Abonnement supprimé');
    },
    error: (erreur) => {
      console.log('Erreur suppression abonnement :', erreur);
    }
  });
}

modifierAbonnement(abonnement: any) {

  this.http.put(
    `http://localhost:8080/abonnements/${abonnement.id}`,
    abonnement
  ).subscribe({

    next: (reponse: any) => {
      console.log('Abonnement modifié :', reponse);
    },

    error: (erreur) => {
      console.log('Erreur modification abonnement :', erreur);
    }

  });
}
}
