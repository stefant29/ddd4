import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUtilizator, NewUtilizator } from '../utilizator.model';

export type PartialUpdateUtilizator = Partial<IUtilizator> & Pick<IUtilizator, 'id'>;

export type EntityResponseType = HttpResponse<IUtilizator>;
export type EntityArrayResponseType = HttpResponse<IUtilizator[]>;

@Injectable({ providedIn: 'root' })
export class UtilizatorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/utilizators');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(utilizator: NewUtilizator): Observable<EntityResponseType> {
    return this.http.post<IUtilizator>(this.resourceUrl, utilizator, { observe: 'response' });
  }

  update(utilizator: IUtilizator): Observable<EntityResponseType> {
    return this.http.put<IUtilizator>(`${this.resourceUrl}/${this.getUtilizatorIdentifier(utilizator)}`, utilizator, {
      observe: 'response',
    });
  }

  partialUpdate(utilizator: PartialUpdateUtilizator): Observable<EntityResponseType> {
    return this.http.patch<IUtilizator>(`${this.resourceUrl}/${this.getUtilizatorIdentifier(utilizator)}`, utilizator, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IUtilizator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUtilizator[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUtilizatorIdentifier(utilizator: Pick<IUtilizator, 'id'>): string {
    return utilizator.id;
  }

  compareUtilizator(o1: Pick<IUtilizator, 'id'> | null, o2: Pick<IUtilizator, 'id'> | null): boolean {
    return o1 && o2 ? this.getUtilizatorIdentifier(o1) === this.getUtilizatorIdentifier(o2) : o1 === o2;
  }

  addUtilizatorToCollectionIfMissing<Type extends Pick<IUtilizator, 'id'>>(
    utilizatorCollection: Type[],
    ...utilizatorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const utilizators: Type[] = utilizatorsToCheck.filter(isPresent);
    if (utilizators.length > 0) {
      const utilizatorCollectionIdentifiers = utilizatorCollection.map(utilizatorItem => this.getUtilizatorIdentifier(utilizatorItem)!);
      const utilizatorsToAdd = utilizators.filter(utilizatorItem => {
        const utilizatorIdentifier = this.getUtilizatorIdentifier(utilizatorItem);
        if (utilizatorCollectionIdentifiers.includes(utilizatorIdentifier)) {
          return false;
        }
        utilizatorCollectionIdentifiers.push(utilizatorIdentifier);
        return true;
      });
      return [...utilizatorsToAdd, ...utilizatorCollection];
    }
    return utilizatorCollection;
  }
}
