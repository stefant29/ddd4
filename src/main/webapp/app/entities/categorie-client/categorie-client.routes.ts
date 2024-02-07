import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CategorieClientComponent } from './list/categorie-client.component';
import { CategorieClientDetailComponent } from './detail/categorie-client-detail.component';
import { CategorieClientUpdateComponent } from './update/categorie-client-update.component';
import CategorieClientResolve from './route/categorie-client-routing-resolve.service';

const categorieClientRoute: Routes = [
  {
    path: '',
    component: CategorieClientComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieClientDetailComponent,
    resolve: {
      categorieClient: CategorieClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieClientUpdateComponent,
    resolve: {
      categorieClient: CategorieClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieClientUpdateComponent,
    resolve: {
      categorieClient: CategorieClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default categorieClientRoute;
