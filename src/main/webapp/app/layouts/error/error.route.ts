import { Routes } from '@angular/router';

import ErrorComponent from './error.component';

export const errorRoute: Routes = [
  {
    path: 'error',
    component: ErrorComponent,
    title: 'Pagină eroare!',
  },
  {
    path: 'accessdenied',
    component: ErrorComponent,
    data: {
      errorMessage: 'Nu sunteți autorizat pentru accesarea acestei pagini.',
    },
    title: 'Pagină eroare!',
  },
  {
    path: '404',
    component: ErrorComponent,
    data: {
      errorMessage: 'Pagina nu există.',
    },
    title: 'Pagină eroare!',
  },
  {
    path: '**',
    redirectTo: '/404',
  },
];
