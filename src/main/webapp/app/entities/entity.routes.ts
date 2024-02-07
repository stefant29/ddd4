import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'companie',
    data: { pageTitle: 'Companies' },
    loadChildren: () => import('./companie/companie.routes'),
  },
  {
    path: 'utilizator',
    data: { pageTitle: 'Utilizators' },
    loadChildren: () => import('./utilizator/utilizator.routes'),
  },
  {
    path: 'categorie-client',
    data: { pageTitle: 'CategorieClients' },
    loadChildren: () => import('./categorie-client/categorie-client.routes'),
  },
  {
    path: 'client',
    data: { pageTitle: 'Clients' },
    loadChildren: () => import('./client/client.routes'),
  },
  {
    path: 'material',
    data: { pageTitle: 'Materials' },
    loadChildren: () => import('./material/material.routes'),
  },
  {
    path: 'proces-verbal',
    data: { pageTitle: 'ProcesVerbals' },
    loadChildren: () => import('./proces-verbal/proces-verbal.routes'),
  },
  {
    path: 'jt-material-proces-verbal',
    data: { pageTitle: 'JTMaterialProcesVerbals' },
    loadChildren: () => import('./jt-material-proces-verbal/jt-material-proces-verbal.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
