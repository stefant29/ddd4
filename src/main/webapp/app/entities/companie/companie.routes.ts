import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CompanieComponent } from './list/companie.component';
import { CompanieDetailComponent } from './detail/companie-detail.component';
import { CompanieUpdateComponent } from './update/companie-update.component';
import CompanieResolve from './route/companie-routing-resolve.service';

const companieRoute: Routes = [
  {
    path: '',
    component: CompanieComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanieDetailComponent,
    resolve: {
      companie: CompanieResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanieUpdateComponent,
    resolve: {
      companie: CompanieResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanieUpdateComponent,
    resolve: {
      companie: CompanieResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default companieRoute;
