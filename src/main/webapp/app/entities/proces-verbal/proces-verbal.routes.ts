import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProcesVerbalComponent } from './list/proces-verbal.component';
import { ProcesVerbalDetailComponent } from './detail/proces-verbal-detail.component';
import { ProcesVerbalUpdateComponent } from './update/proces-verbal-update.component';
import ProcesVerbalResolve from './route/proces-verbal-routing-resolve.service';

const procesVerbalRoute: Routes = [
  {
    path: '',
    component: ProcesVerbalComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcesVerbalDetailComponent,
    resolve: {
      procesVerbal: ProcesVerbalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcesVerbalUpdateComponent,
    resolve: {
      procesVerbal: ProcesVerbalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcesVerbalUpdateComponent,
    resolve: {
      procesVerbal: ProcesVerbalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default procesVerbalRoute;
