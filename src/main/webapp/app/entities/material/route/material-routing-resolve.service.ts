import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMaterial } from '../material.model';
import { MaterialService } from '../service/material.service';

export const materialResolve = (route: ActivatedRouteSnapshot): Observable<null | IMaterial> => {
  const id = route.params['id'];
  if (id) {
    return inject(MaterialService)
      .find(id)
      .pipe(
        mergeMap((material: HttpResponse<IMaterial>) => {
          if (material.body) {
            return of(material.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default materialResolve;
