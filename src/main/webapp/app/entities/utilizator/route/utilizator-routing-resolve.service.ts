import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUtilizator } from '../utilizator.model';
import { UtilizatorService } from '../service/utilizator.service';

export const utilizatorResolve = (route: ActivatedRouteSnapshot): Observable<null | IUtilizator> => {
  const id = route.params['id'];
  if (id) {
    return inject(UtilizatorService)
      .find(id)
      .pipe(
        mergeMap((utilizator: HttpResponse<IUtilizator>) => {
          if (utilizator.body) {
            return of(utilizator.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default utilizatorResolve;
