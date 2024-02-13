import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { JTMaterialProcesVerbalComponent } from './list/jt-material-proces-verbal.component';
import { JTMaterialProcesVerbalDetailComponent } from './detail/jt-material-proces-verbal-detail.component';
import { JTMaterialProcesVerbalUpdateComponent } from './update/jt-material-proces-verbal-update.component';
import JTMaterialProcesVerbalResolve from './route/jt-material-proces-verbal-routing-resolve.service';

const jTMaterialProcesVerbalRoute: Routes = [
  {
    path: '',
    component: JTMaterialProcesVerbalComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JTMaterialProcesVerbalDetailComponent,
    resolve: {
      jTMaterialProcesVerbal: JTMaterialProcesVerbalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JTMaterialProcesVerbalUpdateComponent,
    resolve: {
      jTMaterialProcesVerbal: JTMaterialProcesVerbalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JTMaterialProcesVerbalUpdateComponent,
    resolve: {
      jTMaterialProcesVerbal: JTMaterialProcesVerbalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default jTMaterialProcesVerbalRoute;
