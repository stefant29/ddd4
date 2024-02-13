import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieClient } from '../categorie-client.model';
import { CategorieClientService } from '../service/categorie-client.service';

export const categorieClientResolve = (route: ActivatedRouteSnapshot): Observable<null | ICategorieClient> => {
  const id = route.params['id'];
  if (id) {
    return inject(CategorieClientService)
      .find(id)
      .pipe(
        mergeMap((categorieClient: HttpResponse<ICategorieClient>) => {
          if (categorieClient.body) {
            return of(categorieClient.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default categorieClientResolve;
