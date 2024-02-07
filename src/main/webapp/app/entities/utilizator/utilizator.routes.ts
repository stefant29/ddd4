import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UtilizatorComponent } from './list/utilizator.component';
import { UtilizatorDetailComponent } from './detail/utilizator-detail.component';
import { UtilizatorUpdateComponent } from './update/utilizator-update.component';
import UtilizatorResolve from './route/utilizator-routing-resolve.service';

const utilizatorRoute: Routes = [
  {
    path: '',
    component: UtilizatorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UtilizatorDetailComponent,
    resolve: {
      utilizator: UtilizatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UtilizatorUpdateComponent,
    resolve: {
      utilizator: UtilizatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UtilizatorUpdateComponent,
    resolve: {
      utilizator: UtilizatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default utilizatorRoute;
