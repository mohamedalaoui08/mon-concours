import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const adminGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (!token) {
    return router.createUrlTree(['/connexion']);
  }

  const payload = JSON.parse(atob(token.split('.')[1]));

  if (payload.role === 'ADMIN') {
    return true;
  }

  return router.createUrlTree(['/']);
};