import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProcesVerbal } from '../proces-verbal.model';
import { ProcesVerbalService } from '../service/proces-verbal.service';

export const procesVerbalResolve = (route: ActivatedRouteSnapshot): Observable<null | IProcesVerbal> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProcesVerbalService)
      .find(id)
      .pipe(
        mergeMap((procesVerbal: HttpResponse<IProcesVerbal>) => {
          if (procesVerbal.body) {
            return of(procesVerbal.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default procesVerbalResolve;
