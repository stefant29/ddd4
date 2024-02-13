import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanie } from '../companie.model';
import { CompanieService } from '../service/companie.service';

export const companieResolve = (route: ActivatedRouteSnapshot): Observable<null | ICompanie> => {
  const id = route.params['id'];
  if (id) {
    return inject(CompanieService)
      .find(id)
      .pipe(
        mergeMap((companie: HttpResponse<ICompanie>) => {
          if (companie.body) {
            return of(companie.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default companieResolve;
