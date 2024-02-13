import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJTMaterialProcesVerbal, NewJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';

export type PartialUpdateJTMaterialProcesVerbal = Partial<IJTMaterialProcesVerbal> & Pick<IJTMaterialProcesVerbal, 'id'>;

export type EntityResponseType = HttpResponse<IJTMaterialProcesVerbal>;
export type EntityArrayResponseType = HttpResponse<IJTMaterialProcesVerbal[]>;

@Injectable({ providedIn: 'root' })
export class JTMaterialProcesVerbalService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/jt-material-proces-verbals');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(jTMaterialProcesVerbal: NewJTMaterialProcesVerbal): Observable<EntityResponseType> {
    return this.http.post<IJTMaterialProcesVerbal>(this.resourceUrl, jTMaterialProcesVerbal, { observe: 'response' });
  }

  update(jTMaterialProcesVerbal: IJTMaterialProcesVerbal): Observable<EntityResponseType> {
    return this.http.put<IJTMaterialProcesVerbal>(
      `${this.resourceUrl}/${this.getJTMaterialProcesVerbalIdentifier(jTMaterialProcesVerbal)}`,
      jTMaterialProcesVerbal,
      { observe: 'response' },
    );
  }

  partialUpdate(jTMaterialProcesVerbal: PartialUpdateJTMaterialProcesVerbal): Observable<EntityResponseType> {
    return this.http.patch<IJTMaterialProcesVerbal>(
      `${this.resourceUrl}/${this.getJTMaterialProcesVerbalIdentifier(jTMaterialProcesVerbal)}`,
      jTMaterialProcesVerbal,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IJTMaterialProcesVerbal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJTMaterialProcesVerbal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJTMaterialProcesVerbalIdentifier(jTMaterialProcesVerbal: Pick<IJTMaterialProcesVerbal, 'id'>): string {
    return jTMaterialProcesVerbal.id;
  }

  compareJTMaterialProcesVerbal(o1: Pick<IJTMaterialProcesVerbal, 'id'> | null, o2: Pick<IJTMaterialProcesVerbal, 'id'> | null): boolean {
    return o1 && o2 ? this.getJTMaterialProcesVerbalIdentifier(o1) === this.getJTMaterialProcesVerbalIdentifier(o2) : o1 === o2;
  }

  addJTMaterialProcesVerbalToCollectionIfMissing<Type extends Pick<IJTMaterialProcesVerbal, 'id'>>(
    jTMaterialProcesVerbalCollection: Type[],
    ...jTMaterialProcesVerbalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const jTMaterialProcesVerbals: Type[] = jTMaterialProcesVerbalsToCheck.filter(isPresent);
    if (jTMaterialProcesVerbals.length > 0) {
      const jTMaterialProcesVerbalCollectionIdentifiers = jTMaterialProcesVerbalCollection.map(
        jTMaterialProcesVerbalItem => this.getJTMaterialProcesVerbalIdentifier(jTMaterialProcesVerbalItem)!,
      );
      const jTMaterialProcesVerbalsToAdd = jTMaterialProcesVerbals.filter(jTMaterialProcesVerbalItem => {
        const jTMaterialProcesVerbalIdentifier = this.getJTMaterialProcesVerbalIdentifier(jTMaterialProcesVerbalItem);
        if (jTMaterialProcesVerbalCollectionIdentifiers.includes(jTMaterialProcesVerbalIdentifier)) {
          return false;
        }
        jTMaterialProcesVerbalCollectionIdentifiers.push(jTMaterialProcesVerbalIdentifier);
        return true;
      });
      return [...jTMaterialProcesVerbalsToAdd, ...jTMaterialProcesVerbalCollection];
    }
    return jTMaterialProcesVerbalCollection;
  }
}
