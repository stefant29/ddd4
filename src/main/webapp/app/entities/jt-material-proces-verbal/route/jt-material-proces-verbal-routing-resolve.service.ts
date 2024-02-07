import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';
import { JTMaterialProcesVerbalService } from '../service/jt-material-proces-verbal.service';

export const jTMaterialProcesVerbalResolve = (route: ActivatedRouteSnapshot): Observable<null | IJTMaterialProcesVerbal> => {
  const id = route.params['id'];
  if (id) {
    return inject(JTMaterialProcesVerbalService)
      .find(id)
      .pipe(
        mergeMap((jTMaterialProcesVerbal: HttpResponse<IJTMaterialProcesVerbal>) => {
          if (jTMaterialProcesVerbal.body) {
            return of(jTMaterialProcesVerbal.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default jTMaterialProcesVerbalResolve;
