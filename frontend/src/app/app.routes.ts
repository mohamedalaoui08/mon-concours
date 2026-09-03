import { Routes } from '@angular/router';
import { Accueil } from './accueil/accueil';
import { Connexion } from './connexion/connexion';
import { Concours } from './concours/concours';
import { Qcm } from './qcm/qcm';
import { Exercice } from './exercice/exercice';
import { Formation } from './formation/formation';
import { Actualite } from './actualite/actualite';
import { Abonnement } from './abonnement/abonnement';
import { Profil } from './profil/profil';
import { Admin } from './admin/admin';
import { authGuard } from './auth-guard';
import { adminGuard } from './admin-guard';

export const routes: Routes = [
  { path: '', component: Accueil },
  { path: 'connexion', component: Connexion },
  { path: 'concours', component: Concours },
  { path: 'qcm', component: Qcm, canActivate: [authGuard] },
  { path: 'exercice', component: Exercice, canActivate: [authGuard] },
  { path: 'formation', component: Formation, canActivate: [authGuard] },
  { path: 'actualite', component: Actualite },
  { path: 'abonnement', component: Abonnement },
  { path: 'profil', component: Profil, canActivate: [authGuard] },
  { path: 'admin', component: Admin, canActivate: [adminGuard] }
];