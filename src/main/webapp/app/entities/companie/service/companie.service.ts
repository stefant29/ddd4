import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanie, NewCompanie } from '../companie.model';

export type PartialUpdateCompanie = Partial<ICompanie> & Pick<ICompanie, 'id'>;

export type EntityResponseType = HttpResponse<ICompanie>;
export type EntityArrayResponseType = HttpResponse<ICompanie[]>;

@Injectable({ providedIn: 'root' })
export class CompanieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/companies');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(companie: NewCompanie): Observable<EntityResponseType> {
    return this.http.post<ICompanie>(this.resourceUrl, companie, { observe: 'response' });
  }

  update(companie: ICompanie): Observable<EntityResponseType> {
    return this.http.put<ICompanie>(`${this.resourceUrl}/${this.getCompanieIdentifier(companie)}`, companie, { observe: 'response' });
  }

  partialUpdate(companie: PartialUpdateCompanie): Observable<EntityResponseType> {
    return this.http.patch<ICompanie>(`${this.resourceUrl}/${this.getCompanieIdentifier(companie)}`, companie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompanieIdentifier(companie: Pick<ICompanie, 'id'>): number {
    return companie.id;
  }

  compareCompanie(o1: Pick<ICompanie, 'id'> | null, o2: Pick<ICompanie, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompanieIdentifier(o1) === this.getCompanieIdentifier(o2) : o1 === o2;
  }

  addCompanieToCollectionIfMissing<Type extends Pick<ICompanie, 'id'>>(
    companieCollection: Type[],
    ...companiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const companies: Type[] = companiesToCheck.filter(isPresent);
    if (companies.length > 0) {
      const companieCollectionIdentifiers = companieCollection.map(companieItem => this.getCompanieIdentifier(companieItem)!);
      const companiesToAdd = companies.filter(companieItem => {
        const companieIdentifier = this.getCompanieIdentifier(companieItem);
        if (companieCollectionIdentifiers.includes(companieIdentifier)) {
          return false;
        }
        companieCollectionIdentifiers.push(companieIdentifier);
        return true;
      });
      return [...companiesToAdd, ...companieCollection];
    }
    return companieCollection;
  }
}
